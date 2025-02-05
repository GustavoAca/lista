package com.glaiss.lista.domain.mapper;

import com.glaiss.lista.domain.model.Item;
import com.glaiss.lista.domain.model.ItemLista;
import com.glaiss.lista.domain.model.dto.ItemListaDto;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ItemListaMapper {

    public ItemLista toEntity(ItemListaDto dto) {
        return ItemLista.builder()
                .id(dto.getId())
                .preco(dto.getPreco())
                .listaCompraId(dto.getListaCompraId())
                .quantidade(dto.getQuantidade())
                .item(toItemEntity(dto.getItem()))
                .createdBy(dto.getCreatedBy())
                .createdDate(dto.getCreatedDate())
                .modifiedBy(dto.getModifiedBy())
                .modifiedDate(dto.getModifiedDate())
                .build();
    }

    private Item toItemEntity(UUID itemId) {
        return Item.builder().id(itemId).build();
    }

    public ItemListaDto toDto(ItemLista entity) {
        return ItemListaDto.builder()
                .id(entity.getId())
                .preco(entity.getPreco())
                .quantidade(entity.getQuantidade())
                .listaCompraId(entity.getListaCompraId())
                .item(entity.getItem().getId())
                .createdBy(entity.getCreatedBy())
                .createdDate(entity.getCreatedDate())
                .modifiedBy(entity.getModifiedBy())
                .modifiedDate(entity.getModifiedDate())
                .build();
    }
}
