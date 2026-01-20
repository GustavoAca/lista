package com.glaiss.lista.controller.listacompra.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record ConcluirListaRequestDTO(@NotNull UUID id,
                                      @NotNull BigDecimal valorTotal,
                                      @NotNull String nome,
                                      @NotNull short totalItens,
                                      @NotEmpty @Valid List<ItemListaConcluirRequest> itensLista,
                                      @NotNull long version) {
}
