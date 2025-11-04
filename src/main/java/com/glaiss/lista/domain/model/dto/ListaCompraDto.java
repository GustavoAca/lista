package com.glaiss.lista.domain.model.dto;

import com.glaiss.core.domain.model.dto.EntityAbstractDto;
import com.glaiss.lista.domain.model.ItemLista;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class ListaCompraDto extends EntityAbstractDto {

    @NotNull(message = "O ID não pode ser nulo")
    private UUID id;
    private UUID usuarioId;
    @Builder.Default
    private BigDecimal valorTotal = BigDecimal.ZERO;
    @Builder.Default
    private List<ItemListaDto> itens = new ArrayList<>();
}
