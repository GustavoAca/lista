package com.glaiss.lista.controller.listacompra.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ItemAdicionadoRequest(@NotNull UUID itemOfertaId,
                                    @NotNull short quantidade) {
}
