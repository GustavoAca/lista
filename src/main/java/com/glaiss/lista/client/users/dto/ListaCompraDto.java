package com.glaiss.lista.client.users.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record ListaCompraDto(UUID id,
                             UUID usuarioId,
                             BigDecimal valorTotal) {


}
