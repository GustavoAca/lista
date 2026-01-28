package com.glaiss.lista.controller.listacompra.dto;

import com.glaiss.lista.domain.model.EStatusLista;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record ListaCompraRequest(UUID id,
                                 UUID usuarioId,
                                 String nome,
                                 BigDecimal valorTotal,
                                 short totalItens,
                                 List<ItemListaRequest> itensLista,
                                 long version,
                                 EStatusLista statusLista,
                                 LocalDateTime modifiedDate,
                                 LocalDateTime createdDate) {
}
