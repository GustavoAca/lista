package com.glaiss.lista.domain.service.itemlista;

import com.glaiss.core.domain.model.ResponsePage;
import com.glaiss.core.domain.service.BaseServiceImpl;
import com.glaiss.core.exception.RegistroNaoEncontradoException;
import com.glaiss.lista.controller.listacompra.dto.ItemAdicionadoRequest;
import com.glaiss.lista.controller.listacompra.dto.ItemAlteradoRequest;
import com.glaiss.lista.controller.listacompra.dto.ItemListaRequest;
import com.glaiss.lista.domain.exception.AdicionarItemListaException;
import com.glaiss.lista.domain.mapper.ItemListaMapper;
import com.glaiss.lista.domain.model.ItemLista;
import com.glaiss.lista.domain.model.ItemOferta;
import com.glaiss.lista.domain.model.ListaCompra;
import com.glaiss.lista.domain.model.dto.projection.listacompra.ItemListaProjection;
import com.glaiss.lista.domain.repository.ItemListaRepository;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
public class ItemListaServiceImpl extends BaseServiceImpl<ItemLista, UUID, ItemListaRepository> implements ItemListaService {

    private final ItemListaMapper itemListaMapper;
    private final EntityManager em;

    protected ItemListaServiceImpl(ItemListaRepository repo,
                                   ItemListaMapper itemListaMapper,
                                   EntityManager em) {
        super(repo);
        this.itemListaMapper = itemListaMapper;
        this.em = em;
    }

    @Override
    public ResponsePage<ItemListaProjection> listarItensPorListaCompraIdPaginaDTO(Pageable pageable, UUID listId) {
        Page<ItemListaProjection> itemPage = repo.findAllByListaCompra_Id(pageable, listId);
        return new ResponsePage<>(itemPage);
    }

    @Override
    @Transactional
    public List<ItemListaRequest> adicionaLista(UUID listaId, List<ItemAdicionadoRequest> itensDto) {
        ListaCompra listaCompraRef = em.getReference(ListaCompra.class, listaId);
        
        List<UUID> itemOfertaIds = itensDto.stream().map(ItemAdicionadoRequest::itemOfertaId).toList();
        List<ItemLista> itensExistentes = repo.findAllByListaCompra_IdAndItemOferta_IdIn(listaId, itemOfertaIds);
        
        Map<UUID, ItemLista> mapaItensExistentes = itensExistentes.stream()
                .collect(java.util.stream.Collectors.toMap(ItemLista::getItemOfertaId, item -> item));

        List<ItemLista> itensParaSalvar = new LinkedList<>();

        for (ItemAdicionadoRequest itemAdicionadoRequest : itensDto) {
            ItemLista itemLista = mapaItensExistentes.get(itemAdicionadoRequest.itemOfertaId());
            if (itemLista != null) {
                itemLista.adicionarQuantidade(itemAdicionadoRequest.quantidade());
            } else {
                ItemOferta itemOfertaRef = em.getReference(ItemOferta.class, itemAdicionadoRequest.itemOfertaId());
                itemLista = ItemLista.builder()
                        .listaCompra(listaCompraRef)
                        .itemOferta(itemOfertaRef)
                        .quantidade(itemAdicionadoRequest.quantidade())
                        .build();
            }
            itensParaSalvar.add(itemLista);
        }
        
        try {
            return repo.saveAll(itensParaSalvar).stream()
                    .map(itemListaMapper::toDto)
                    .toList();
        } catch (Exception e) {
            log.error("Erro ao salvar itens na lista {}", listaId, e);
            throw new AdicionarItemListaException();
        }
    }

    @Override
    public void salvarAll(List<ItemLista> itemListas) {
        repo.saveAll(itemListas);
    }

    @Override
    public List<ItemLista> buscarTodosPorLista(UUID listaId) {
        return repo.findAllByListaCompraId(listaId);
    }

    @Override
    @Transactional
    public Boolean alterarItens(UUID listaId, List<ItemAlteradoRequest> itensAlteradosDto) {
        try {
            List<UUID> itensIds = itensAlteradosDto.stream().map(ItemAlteradoRequest::id).toList();
            List<ItemLista> itensExistentes = repo.findAllByIdInAndListaCompra_Id(itensIds, listaId);

            if (itensExistentes.size() != itensAlteradosDto.size()) {
                 log.warn("Alguns itens não foram encontrados na lista {} para alteração", listaId);
                 // Opcional: throw exception ou continuar com os encontrados
            }

            Map<UUID, ItemLista> mapaItens = itensExistentes.stream()
                    .collect(java.util.stream.Collectors.toMap(ItemLista::getId, item -> item));

            List<ItemLista> paraSalvar = new LinkedList<>();
            List<ItemLista> paraDeletar = new LinkedList<>();

            for (ItemAlteradoRequest itemAlterado : itensAlteradosDto) {
                ItemLista itemLista = mapaItens.get(itemAlterado.id());
                if (itemLista != null) {
                    itemLista.alterarQuantidade(itemAlterado.quantidade());
                    if (itemLista.getQuantidade() <= 0) {
                        paraDeletar.add(itemLista);
                    } else {
                        paraSalvar.add(itemLista);
                    }
                }
            }

            if (!paraDeletar.isEmpty()) {
                repo.deleteAll(paraDeletar);
            }
            if (!paraSalvar.isEmpty()) {
                repo.saveAll(paraSalvar);
            }

            return Boolean.TRUE;
        } catch (Exception e) {
            log.error("Erro ao alterar itens na lista {}", listaId, e);
            return Boolean.FALSE;
        }
    }

    @Override
    public void salvarAllConcluindoLista(List<ItemLista> itensLista) {
        itensLista.forEach(itemLista -> {
            if (itemLista.getItemOferta() != null) {
                itemLista.setPrecoUnitario(itemLista.getItemOferta().getPreco());
            }
            repo.save(itemLista);
        });
    }

    @Override
    @Transactional
    public Boolean removerItem(UUID listaId, UUID itemId) {
        try {
            ItemLista item = repo.findById(itemId)
                    .orElseThrow(() -> new RegistroNaoEncontradoException("Item Lista"));
            
            if (!item.getListaCompra().getId().equals(listaId)) {
                return Boolean.FALSE;
            }

            repo.delete(item);
            return Boolean.TRUE;
        } catch (Exception e) {
            log.error("Erro ao remover item {} da lista {}", itemId, listaId, e);
            return Boolean.FALSE;
        }
    }
}
