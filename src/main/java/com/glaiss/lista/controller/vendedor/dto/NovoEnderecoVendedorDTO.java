package com.glaiss.lista.controller.vendedor.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record NovoEnderecoVendedorDTO(@NotNull UUID vendedorId,
                                      @NotEmpty @Valid List<EnderecoDTO> enderecos,
                                      long version) {
}
