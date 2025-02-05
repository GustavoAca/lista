package com.glaiss.lista.domain.mapper;

import com.glaiss.lista.domain.model.ItemLista;
import com.glaiss.lista.domain.model.dto.ItemListaDto;
import org.springframework.stereotype.Component;

@Component
public class ItemListaMapper {

    public ItemLista toEntity(ItemListaDto dto) {
        return ItemLista.builder()
                .id(dto.getId())
                .preco(dto.getPreco())
                .listaCompraId(dto.getListaCompraId())
                .quantidade(dto.getQuantidade())
                .createdBy(dto.getCreatedBy())
                .createdDate(dto.getCreatedDate())
                .modifiedBy(dto.getModifiedBy())
                .modifiedDate(dto.getModifiedDate())
                .build();
    }

    public ItemListaDto toDto(ItemLista entity) {
        return ItemListaDto.builder()
                .id(entity.getId())
                .preco(entity.getPreco())
                .quantidade(entity.getQuantidade())
                .listaCompraId(entity.getListaCompraId())
                .createdBy(entity.getCreatedBy())
                .createdDate(entity.getCreatedDate())
                .modifiedBy(entity.getModifiedBy())
                .modifiedDate(entity.getModifiedDate())
                .build();
    }
}
