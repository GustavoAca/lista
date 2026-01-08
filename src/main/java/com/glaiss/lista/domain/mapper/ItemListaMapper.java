package com.glaiss.lista.domain.mapper;

import com.glaiss.lista.controller.dto.ItemListaDTO;
import com.glaiss.lista.domain.model.ItemLista;
import com.glaiss.lista.domain.model.ItemOferta;
import com.glaiss.lista.domain.model.ListaCompra;
import org.springframework.stereotype.Component;

@Component
public class ItemListaMapper {

    public ItemLista toEntity(ItemListaDTO itemListaDTO) {
        return ItemLista.builder()
                .id(itemListaDTO.id())
                .listaCompra(ListaCompra.builder().id(itemListaDTO.listaCompraId()).build())
                .itemOferta(ItemOferta.builder().id(itemListaDTO.itemOfertaId()).build())
                .quantidade(itemListaDTO.quantidade())
                .build();
    }

    public ItemListaDTO toDto(ItemLista itemLista) {
        return new ItemListaDTO(itemLista.getId(),
                itemLista.getListaCompraId(),
                itemLista.getItemOfertaId(),
                itemLista.getQuantidade());
    }
}
