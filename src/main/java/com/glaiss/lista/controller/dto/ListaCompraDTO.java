package com.glaiss.lista.controller.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record ListaCompraDTO(UUID id,
                             UUID usuarioId,
                             String nome,
                             BigDecimal valorTotal,
                             short totalItens,
                             List<ItemListaDTO> itensLista) {
}
