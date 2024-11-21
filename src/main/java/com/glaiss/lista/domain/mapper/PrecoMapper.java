package com.glaiss.lista.domain.mapper;

import com.glaiss.lista.domain.model.Preco;
import com.glaiss.lista.domain.model.dto.PrecoDto;
import org.springframework.stereotype.Component;

@Component
public class PrecoMapper {

    public Preco toEntity(PrecoDto precoDto) {
        return Preco.builder()
                .id(precoDto.getId())
                .preco(precoDto.getPreco())
                .localId(precoDto.getLocalId())
                .createdBy(precoDto.getCreatedBy())
                .modifiedBy(precoDto.getModifiedBy())
                .createdDate(precoDto.getCreatedDate())
                .modifiedDate(precoDto.getModifiedDate())
                .build();
    }

    public PrecoDto toDto(Preco entity) {
        return PrecoDto.builder()
                .id(entity.getId())
                .preco(entity.getPreco())
                .localId(entity.getLocalId())
                .createdBy(entity.getCreatedBy())
                .createdDate(entity.getCreatedDate())
                .build();
    }
}
