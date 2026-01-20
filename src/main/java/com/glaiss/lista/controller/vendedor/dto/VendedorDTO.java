package com.glaiss.lista.controller.vendedor.dto;

import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record VendedorDTO(UUID id,
                          @NotNull String nome,
                          List<EnderecoDTO> enderecos,
                          long version) {
}
