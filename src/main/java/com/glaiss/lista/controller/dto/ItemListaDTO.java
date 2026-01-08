package com.glaiss.lista.controller.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record ItemListaDTO(UUID id,
                           UUID listaCompraId,
                           UUID itemOfertaId,
                           @NotBlank short quantidade) {
}
