package com.glaiss.lista.domain.model.dto;

import com.glaiss.core.domain.model.dto.EntityAbstractDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class ItemListaDto extends EntityAbstractDto {

    private ItemDto item;
    private UUID id;
    private BigDecimal preco;
    private Integer quantidade;
    private UUID listaCompra;
}
