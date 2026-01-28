package com.glaiss.lista.controller.listacompra.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ListaCompraEdicaoRequest(@NotNull UUID id,
                                       @NotNull String nome,
                                       @NotNull long version) {
}
