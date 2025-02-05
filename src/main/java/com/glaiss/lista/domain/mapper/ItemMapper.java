package com.glaiss.lista.domain.mapper;

import com.glaiss.lista.domain.model.Item;
import com.glaiss.lista.domain.model.Preco;
import com.glaiss.lista.domain.model.dto.ItemDto;
import com.glaiss.lista.domain.model.dto.PrecoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ItemMapper {

    private final PrecoMapper precoMapper;

    @Autowired
    public ItemMapper(PrecoMapper precoMapper) {
        this.precoMapper = precoMapper;
    }

    public Item toEntity(ItemDto dto){
        return Item.builder()
                .id(dto.getId())
                .nome(dto.getNome())
                .peso(dto.getPeso())
                .marca(dto.getMarca())
                .precos(precoToEntity(dto.getPrecos()))
                .createdBy(dto.getCreatedBy())
                .createdDate(dto.getCreatedDate())
                .modifiedBy(dto.getModifiedBy())
                .modifiedDate(dto.getModifiedDate())
                .build();
    }

    public List<Preco> precoToEntity(List<PrecoDto> precoDto) {
        return precoDto.stream().map(precoMapper::toEntity).toList();
    }

    public ItemDto toDto(Item entity) {
        return ItemDto.builder()
                .id(entity.getId())
                .nome(entity.getNome())
                .peso(entity.getPeso())
                .marca(entity.getMarca())
                .precos(precoToDto(entity.getPrecos()))
                .createdBy(entity.getCreatedBy())
                .createdDate(entity.getCreatedDate())
                .modifiedBy(entity.getModifiedBy())
                .modifiedDate(entity.getModifiedDate())
                .build();
    }

    public List<PrecoDto> precoToDto(List<Preco> entity) {
        return entity.stream().map(precoMapper::toDto).toList();
    }
}
