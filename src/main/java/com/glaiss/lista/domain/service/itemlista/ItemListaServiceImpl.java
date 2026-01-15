package com.glaiss.lista.domain.service.itemlista;

import com.glaiss.core.domain.model.ResponsePage;
import com.glaiss.core.domain.service.BaseServiceImpl;
import com.glaiss.core.exception.RegistroNaoEncontradoException;
import com.glaiss.lista.controller.dto.ItemAdicionadoDTO;
import com.glaiss.lista.controller.dto.ItemAlteradoDTO;
import com.glaiss.lista.controller.dto.ItemListaDTO;
import com.glaiss.lista.domain.mapper.ItemListaMapper;
import com.glaiss.lista.domain.model.ItemLista;
import com.glaiss.lista.domain.model.ItemOferta;
import com.glaiss.lista.domain.model.ListaCompra;
import com.glaiss.lista.domain.model.dto.projection.ItemListaProjection;
import com.glaiss.lista.domain.repository.ItemListaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Service
public class ItemListaServiceImpl extends BaseServiceImpl<ItemLista, UUID, ItemListaRepository> implements ItemListaService {

    private final ItemListaMapper itemListaMapper;

    protected ItemListaServiceImpl(ItemListaRepository repo,
                                   ItemListaMapper itemListaMapper) {
        super(repo);
        this.itemListaMapper = itemListaMapper;
    }

    @Override
    public ResponsePage<ItemListaProjection> listarItensPorListaCompraIdPaginaDTO(Pageable pageable, UUID listId) {
        Page<ItemListaProjection> itemPage = repo.findAllByListaCompra_Id(pageable, listId);
        return new ResponsePage<>(itemPage);
    }

    @Override
    public List<ItemListaDTO> adicionaLista(UUID listaId, List<ItemAdicionadoDTO> itensDto) {
        List<ItemLista> itemListas = new LinkedList<>();
        for (ItemAdicionadoDTO itemAdicionadoDTO : itensDto) {
            ItemLista itemLista = repo.findByListaCompra_IdAndItemOferta_Id(listaId, itemAdicionadoDTO.itemOfertaId())
                    .map(existente -> {
                        existente.adicionarQuantidade(itemAdicionadoDTO.quantidade());
                        return existente;
                    })
                    .orElseGet(() -> criarNovoItem(listaId, itemAdicionadoDTO.itemOfertaId(), itemAdicionadoDTO.quantidade()));
            itemListas.add(itemLista);
        }

        return repo.saveAll(itemListas)
                .stream().map(itemListaMapper::toDto)
                .toList();
    }

    private ItemLista criarNovoItem(UUID listaId, UUID itemOfertaId, short quantidade) {
        return ItemLista.builder()
                .itemOferta(ItemOferta.builder()
                        .id(itemOfertaId)
                        .build())
                .quantidade(quantidade)
                .listaCompra(ListaCompra.builder()
                        .id(listaId)
                        .build())
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
    public Boolean alterarItens(UUID listaId, List<ItemAlteradoDTO> itensAlterados) {
        try {
            List<ItemLista> itensLista = new LinkedList<>();
            for (ItemAlteradoDTO itemAlterado : itensAlterados) {
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
}
