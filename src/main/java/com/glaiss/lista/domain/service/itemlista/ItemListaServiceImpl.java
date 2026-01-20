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

import java.util.LinkedList;
import java.util.List;
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
    public List<ItemListaRequest> adicionaLista(UUID listaId, List<ItemAdicionadoRequest> itensDto) {
        List<ItemLista> itemListas = new LinkedList<>();
        for (ItemAdicionadoRequest itemAdicionadoRequest : itensDto) {
            ItemLista itemLista = repo.findByListaCompra_IdAndItemOferta_Id(listaId, itemAdicionadoRequest.itemOfertaId())
                    .map(existente -> {
                        existente.adicionarQuantidade(itemAdicionadoRequest.quantidade());
                        return existente;
                    })
                    .orElseGet(() -> criarNovoItem(listaId, itemAdicionadoRequest.itemOfertaId(), itemAdicionadoRequest.quantidade()));
            itemListas.add(itemLista);
        }
        try {
            return repo.saveAll(itemListas)
                    .stream().map(itemListaMapper::toDto)
                    .toList();
        }catch (Exception e){
            log.error("Erro ao salvar", e);
            throw new AdicionarItemListaException();
        }
    }

    private ItemLista criarNovoItem(UUID listaId, UUID itemOfertaId, short quantidade) {
        return ItemLista.builder()
                .itemOferta(em.getReference(ItemOferta.class, itemOfertaId))
                .quantidade(quantidade)
                .listaCompra(em.getReference(ListaCompra.class, listaId))
                .build();
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
    public Boolean alterarItens(UUID listaId, List<ItemAlteradoRequest> itensAlterados) {
        try {
            List<ItemLista> itensLista = new LinkedList<>();
            for (ItemAlteradoRequest itemAlterado : itensAlterados) {
                ItemLista itemLista = repo
                        .findById(itemAlterado.id())
                        .map(existente -> {
                            existente.alterarQuantidade(itemAlterado.quantidade());
                            return existente;
                        })
                        .orElseThrow(() -> new RegistroNaoEncontradoException("Item Lista"));
                if(itemLista.getQuantidade() == 0){
                    repo.delete(itemLista);
                    continue;
                }
                itensLista.add(itemLista);
            }

            repo.saveAll(itensLista);
            return Boolean.TRUE;
        } catch (Exception e) {
            return Boolean.FALSE;
        }
    }

    @Override
    public void salvarAllConcluindoLista(List<ItemLista> itensLista) {
        itensLista.forEach(itemLista -> {
            itemLista.setPrecoUnitario(itemLista.getItemOferta().getPreco());
            repo.save(itemLista);
        });
    }
}
