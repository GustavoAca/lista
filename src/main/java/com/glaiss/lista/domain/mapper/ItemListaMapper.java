package com.glaiss.lista.domain.mapper;

import com.glaiss.lista.controller.listacompra.dto.ItemListaConcluirRequest;
import com.glaiss.lista.controller.listacompra.dto.ItemListaRequest;
import com.glaiss.lista.controller.listacompra.dto.ItemOfertaConcluirRequest;
import com.glaiss.lista.domain.model.ItemLista;
import com.glaiss.lista.domain.model.ItemOferta;
import com.glaiss.lista.domain.model.ListaCompra;
import org.springframework.stereotype.Component;

@Component
public class ItemListaMapper {

    private final ItemOfertaMapper itemOfertaMapper;

    public ItemListaMapper(ItemOfertaMapper itemOfertaMapper) {
        this.itemOfertaMapper = itemOfertaMapper;
    }

    public ItemLista toEntity(ItemListaRequest itemListaRequest) {
        return ItemLista.builder()
                .id(itemListaRequest.id())
                .listaCompra(ListaCompra.builder().id(itemListaRequest.listaCompraId()).build())
                .itemOferta(ItemOferta.builder().id(itemListaRequest.itemOfertaId()).build())
                .quantidade(itemListaRequest.quantidade())
                .version(itemListaRequest.version())
                .build();
    }

    public ItemListaRequest toDto(ItemLista itemLista) {
        return new ItemListaRequest(itemLista.getId(),
                itemLista.getListaCompraId(),
                itemLista.getItemOfertaId(),
                itemLista.getQuantidade(),
                itemLista.getVersion());
    }

    public ItemLista itemListaConcluirRequestToItemListaEntity(ItemListaConcluirRequest itemListaConcluirRequests) {
        return ItemLista.builder()
                .id(itemListaConcluirRequests.id())
                .listaCompra(ListaCompra.builder().id(itemListaConcluirRequests.listaCompraId()).build())
                .itemOferta(itemOfertaConcluirRequestToItemOferta(itemListaConcluirRequests.itemOferta()))
                .quantidade(itemListaConcluirRequests.quantidade())
                .version(itemListaConcluirRequests.version())
                .build();
    }

    private ItemOferta itemOfertaConcluirRequestToItemOferta(ItemOfertaConcluirRequest itemOfertaConcluirRequest){
        return itemOfertaMapper.itemOfertaConcluirRequestToEntity(itemOfertaConcluirRequest);
    }
}
