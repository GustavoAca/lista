package com.glaiss.lista.controller.listacompra.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ItemListaConcluirRequest(@NotNull UUID id,
                                       @NotNull @Valid ItemOfertaConcluirRequest itemOferta,
                                       @NotNull UUID listaCompraId,
                                       @NotNull short quantidade,
                                       @NotNull long version) {
}
