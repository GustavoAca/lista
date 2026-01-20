package com.glaiss.lista.controller.listacompra.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record ItemListaRequest(UUID id,
                               UUID listaCompraId,
                               UUID itemOfertaId,
                               @NotBlank short quantidade,
                               long version) {
}
