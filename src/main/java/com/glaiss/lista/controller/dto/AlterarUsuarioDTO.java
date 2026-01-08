package com.glaiss.lista.controller.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record AlterarUsuarioDTO(@Valid @NotNull String email, @Valid @NotNull String senha) {
}
