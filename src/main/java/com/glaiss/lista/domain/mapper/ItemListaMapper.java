package com.glaiss.lista.domain.mapper;

import com.glaiss.lista.domain.model.Item;
import com.glaiss.lista.domain.model.ItemLista;
import com.glaiss.lista.domain.model.dto.ItemDto;
import com.glaiss.lista.domain.model.dto.ItemListaDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ItemListaMapper {

    private final ItemMapper itemMapper;

    @Autowired
    public ItemListaMapper(ItemMapper itemMapper) {
        this.itemMapper = itemMapper;
    }

    public ItemLista toEntity(ItemListaDto dto) {
        return ItemLista.builder()
                .id(dto.getId())
                .item(toEntity(dto.getItem()))
                .preco(dto.getPreco())
                .listaCompra(dto.getListaCompra())
                .quantidade(dto.getQuantidade())
                .createdBy(dto.getCreatedBy())
                .createdDate(dto.getCreatedDate())
                .modifiedBy(dto.getModifiedBy())
                .modifiedDate(dto.getModifiedDate())
                .build();
    }

    private Item toEntity(ItemDto itemDto) {
        return itemMapper.toEntity(itemDto);
    }

    public ItemListaDto toDto(ItemLista entity) {
        return ItemListaDto.builder()
                .id(entity.getId())
                .item(itemToDo(entity.getItem()))
                .preco(entity.getPreco())
                .quantidade(entity.getQuantidade())
                .listaCompra(entity.getListaCompra())
                .createdBy(entity.getCreatedBy())
                .createdDate(entity.getCreatedDate())
                .build();
    }

    private ItemDto itemToDo(Item entity) {
        return itemMapper.toDto(entity);
    }
}
