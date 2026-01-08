package com.glaiss.lista.controller.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record CreateUsuarioDTO(@Valid @NotNull String email,
                               @Valid @NotNull String nome,
                               @Valid @NotNull String senha) {
}
