package com.glaiss.lista.controller.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ItemAdicionadoDTO(@NotNull UUID itemOfertaId,
                                @NotNull short quantidade) {
}
