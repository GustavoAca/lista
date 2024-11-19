package com.glaiss.lista.domain.mapper;

import com.glaiss.lista.domain.model.ListaCompra;
import com.glaiss.lista.domain.model.dto.ListaCompraDto;
import org.springframework.stereotype.Component;

@Component
public class ListaCompraMapper {

    public ListaCompra toEntity(ListaCompraDto dto) {
        return ListaCompra.builder()
                .id(dto.getId())
                .usuarioId(dto.getUsuarioId())
                .createdBy(dto.getCreatedBy())
                .createdDate(dto.getCreatedDate())
                .modifiedBy(dto.getModifiedBy())
                .modifiedDate(dto.getModifiedDate())
                .build();
    }

    public ListaCompra toDto(ListaCompra entity) {
        return ListaCompra.builder()
                .id(entity.getId())
                .usuarioId(entity.getUsuarioId())
                .createdBy(entity.getCreatedBy())
                .createdDate(entity.getCreatedDate())
                .build();
    }
}
