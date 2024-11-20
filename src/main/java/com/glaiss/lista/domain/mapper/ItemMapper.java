package com.glaiss.lista.domain.mapper;

import com.glaiss.lista.domain.model.Item;
import com.glaiss.lista.domain.model.dto.ItemDto;
import org.springframework.stereotype.Component;

@Component
public class ItemMapper {

    public Item toEntity(ItemDto dto){
        return Item.builder()
                .id(dto.getId())
                .nome(dto.getNome())
                .peso(dto.getPeso())
                .marca(dto.getMarca())
                .precos(dto.getPrecos())
                .createdBy(dto.getCreatedBy())
                .createdDate(dto.getCreatedDate())
                .modifiedBy(dto.getModifiedBy())
                .modifiedDate(dto.getModifiedDate())
                .build();
    }

    public ItemDto toDto(Item dto){
        return ItemDto.builder()
                .id(dto.getId())
                .nome(dto.getNome())
                .peso(dto.getPeso())
                .marca(dto.getMarca())
                .precos(dto.getPrecos())
                .createdBy(dto.getCreatedBy())
                .createdDate(dto.getCreatedDate())
                .build();
    }
}
