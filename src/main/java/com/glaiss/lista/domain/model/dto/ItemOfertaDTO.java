package com.glaiss.lista.domain.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record ItemOfertaDTO(UUID id,
                            @NotNull UUID itemId,
                            @NotNull UUID vendedorId,
                            Boolean hasPromocaoAtiva,
                            @NotNull BigDecimal preco,
                            @JsonInclude(JsonInclude.Include.NON_NULL) LocalDateTime dataInicioPromocao,
                            @JsonInclude(JsonInclude.Include.NON_NULL) LocalDateTime dataFinalPromocao) {
}
