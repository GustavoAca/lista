package com.glaiss.lista.domain.mapper;

import com.glaiss.lista.controller.dto.ItemDTO;
import com.glaiss.lista.domain.model.Item;
import org.springframework.stereotype.Component;

@Component
public class ItemMapper {

    public ItemDTO toDto(Item item){
        return new ItemDTO(item.getId(),
                item.getIsAtivo(),
                item.getNome(),
                item.getDescricao());
    }

    public Item toEntity(ItemDTO itemDTO){
        return Item.builder()
                .id(itemDTO.id())
                .descricao(itemDTO.descricao())
                .nome(itemDTO.nome())
                .isAtivo(itemDTO.isAtivo())
                .build();
    }
}
