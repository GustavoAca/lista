package com.glaiss.lista.controller.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record ItemDTO(UUID id,
                      Boolean isAtivo,
                      @NotBlank String nome,
                      @NotBlank String descricao) {
}
