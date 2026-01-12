package com.glaiss.lista.domain.service.itemlista;

import com.glaiss.core.domain.model.ResponsePage;
import com.glaiss.core.domain.service.BaseServiceImpl;
import com.glaiss.lista.controller.dto.ItemAdicionadoDTO;
import com.glaiss.lista.controller.dto.ItemListaDTO;
import com.glaiss.lista.domain.mapper.ItemListaMapper;
import com.glaiss.lista.domain.model.ItemLista;
import com.glaiss.lista.domain.model.ItemOferta;
import com.glaiss.lista.domain.model.ListaCompra;
import com.glaiss.lista.domain.repository.ItemListaRepository;
import jakarta.transaction.Transactional;
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
    public ResponsePage<ItemListaDTO> listarItensPorListaCompraIdPaginaDTO(Pageable pageable, UUID listId) {
        Page<ItemLista> itemPage = repo.findAllByListaCompra_Id(pageable, listId);
        List<ItemListaDTO> listaItem = itemPage.getContent().stream().map(itemListaMapper::toDto).toList();
        return new ResponsePage<>(listaItem, pageable.getPageNumber(), pageable.getPageSize(), itemPage.getTotalElements());
    }

    @Override
    public List<ItemListaDTO> adicionaLista(UUID listaId, List<ItemAdicionadoDTO> itensDto) {
        List<ItemLista> itens = new LinkedList<>();
        for (ItemAdicionadoDTO itemAdicionadoDTO : itensDto) {
            ItemLista item = repo
                    .findByListaCompra_IdAndItemOferta_Id(listaId, itemAdicionadoDTO.itemOfertaId())
                    .map(existente -> {
                        existente.adicionarQuantidade(itemAdicionadoDTO.quantidade());
                        return existente;
                    })
                    .orElseGet(() -> criarNovoItem(listaId, itemAdicionadoDTO.itemOfertaId(), itemAdicionadoDTO.quantidade()));
            itens.add(item);
        }

        return repo.saveAll(itens)
                .stream().map(itemListaMapper::toDto)
                .toList();
    }

    private ItemLista criarNovoItem(UUID listaId, UUID itemOfertaId, short quantidade) {
        return repo.save(ItemLista.builder()
                .itemOferta(ItemOferta.builder()
                        .id(itemOfertaId)
                        .build())
                .quantidade(quantidade)
                .listaCompra(ListaCompra.builder()
                        .id(listaId)
                        .build())
                .build());
    }

    @Override
    public void salvarAll(List<ItemLista> itemListas) {
        repo.saveAll(itemListas);
    }

    @Override
    @Transactional
    public Boolean removerDaLista(UUID listaId, List<UUID> itensLista) {
        try {
            repo.deleteItensDaLista(listaId, itensLista);
            return Boolean.TRUE;
        } catch (Exception e) {
            return Boolean.FALSE;
        }
    }
}
