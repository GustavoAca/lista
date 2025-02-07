package com.glaiss.lista.client.users.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;
import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ListaCompraDto(UUID id,
                             UUID usuarioId,
                             BigDecimal valorTotal) {
}
