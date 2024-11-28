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

    public ItemDto toDto(Item dto){
        return ItemDto.builder()
                .id(dto.getId())
                .nome(dto.getNome())
                .peso(dto.getPeso())
                .marca(dto.getMarca())
                .precos(precoToDto(dto.getPrecos()))
                .createdBy(dto.getCreatedBy())
                .createdDate(dto.getCreatedDate())
                .build();
    }

    public List<PrecoDto> precoToDto(List<Preco> entity) {
        return entity.stream().map(precoMapper::toDto).toList();
    }
}
