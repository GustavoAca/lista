package com.glaiss.lista.domain.mapper;

import com.glaiss.lista.controller.dto.ItemListaDTO;
import com.glaiss.lista.controller.dto.ListaCompraDTO;
import com.glaiss.lista.domain.model.ItemLista;
import com.glaiss.lista.domain.model.ListaCompra;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class ListaCompraMapper {

    private final ItemListaMapper itemListaMapper;

    public ListaCompraMapper(ItemListaMapper itemListaMapper) {
        this.itemListaMapper = itemListaMapper;
    }

    public ListaCompra toEntity(ListaCompraDTO listaCompraDTO) {
        return ListaCompra.builder()
                .id(listaCompraDTO.id())
                .usuarioId(listaCompraDTO.usuarioId())
                .valorTotal(listaCompraDTO.valorTotal())
                .totalItens(listaCompraDTO.totalItens())
                .itensLista(toItemListaEntity(listaCompraDTO.itensLista()))
                .build();
    }

    private List<ItemLista> toItemListaEntity(List<ItemListaDTO> itensListaDTO) {
        if (Objects.isNull(itensListaDTO) || itensListaDTO.isEmpty()) {
            return null;
        }
        return itensListaDTO.stream().map(itemListaMapper::toEntity).toList();
    }

    public ListaCompraDTO toDto(ListaCompra entity) {
        return new ListaCompraDTO(entity.getId(),
                entity.getUsuarioId(),
                entity.getValorTotal(),
                entity.getTotalItens(),
                (toItemListaDto(entity.getItensLista())));
    }

    private List<ItemListaDTO> toItemListaDto(List<ItemLista> itemListas) {
        if (Objects.isNull(itemListas) || itemListas.isEmpty()) {
            return null;
        }
        return itemListas.stream().map(itemListaMapper::toDto).toList();
    }
}
