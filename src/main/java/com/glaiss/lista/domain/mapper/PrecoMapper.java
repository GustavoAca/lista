package com.glaiss.lista.domain.mapper;

import com.glaiss.lista.domain.model.Item;
import com.glaiss.lista.domain.model.Preco;
import com.glaiss.lista.domain.model.dto.PrecoDto;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PrecoMapper {

    public Preco toEntity(PrecoDto precoDto) {
        return Preco.builder()
                .id(precoDto.getId())
                .localId(precoDto.getLocalId())
                .valor(precoDto.getValor())
                .item(toItemEntity(precoDto.getItemId()))
                .createdBy(precoDto.getCreatedBy())
                .modifiedBy(precoDto.getModifiedBy())
                .createdDate(precoDto.getCreatedDate())
                .modifiedDate(precoDto.getModifiedDate())
                .build();
    }

    private Item toItemEntity(UUID itemId) {
        return Item.builder().id(itemId).build();
    }

    public PrecoDto toDto(Preco entity) {
        return PrecoDto.builder()
                .id(entity.getId())
                .localId(entity.getLocalId())
                .valor(entity.getValor())
                .itemId(entity.getItem().getId())
                .createdBy(entity.getCreatedBy())
                .createdDate(entity.getCreatedDate())
                .modifiedBy(entity.getModifiedBy())
                .modifiedDate(entity.getModifiedDate())
                .build();
    }
}
