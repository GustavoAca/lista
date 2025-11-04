package com.glaiss.lista.domain.mapper;

import com.glaiss.lista.domain.model.ItemLista;
import com.glaiss.lista.domain.model.ListaCompra;
import com.glaiss.lista.domain.model.dto.ItemListaDto;
import com.glaiss.lista.domain.model.dto.ListaCompraDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class ListaCompraMapper {

    private final ItemListaMapper itemListaMapper;

    @Autowired
    public ListaCompraMapper(ItemListaMapper itemListaMapper) {
        this.itemListaMapper = itemListaMapper;
    }

    public ListaCompra toEntity(ListaCompraDto dto) {
        return ListaCompra.builder()
                .id(dto.getId())
                .usuarioId(dto.getUsuarioId())
                .valorTotal(dto.getValorTotal())
                .createdBy(dto.getCreatedBy())
                .createdDate(dto.getCreatedDate())
                .modifiedBy(dto.getModifiedBy())
                .modifiedDate(dto.getModifiedDate())
                .itens(dto.getItens() == null ? List.of() : toItemListaEntity(dto.getItens()))
                .build();
    }

    private List<ItemLista> toItemListaEntity(List<ItemListaDto> itensDto){
        return Objects.isNull(itensDto) || itensDto.isEmpty() ? null :  itensDto.stream().map(itemListaMapper::toEntity).toList();
    }

    public ListaCompraDto toDto(ListaCompra entity) {
        return ListaCompraDto.builder()
                .id(entity.getId())
                .usuarioId(entity.getUsuarioId())
                .valorTotal(entity.getValorTotal())
                .createdBy(entity.getCreatedBy())
                .createdDate(entity.getCreatedDate())
                .modifiedBy(entity.getModifiedBy())
                .modifiedDate(entity.getModifiedDate())
                .itens(toItemListaDto(entity.getItens()))
                .build();
    }

    private List<ItemListaDto> toItemListaDto(List<ItemLista> itens){
        return Objects.isNull(itens) || itens.isEmpty() ? null : itens.stream().map(itemListaMapper::toDto).toList();
    }
}
