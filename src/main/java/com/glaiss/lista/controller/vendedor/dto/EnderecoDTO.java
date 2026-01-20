package com.glaiss.lista.controller.vendedor.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record EnderecoDTO(@NotNull String complemento,
                          @NotNull String cep,
                          @NotNull String logradouro,
                          @NotNull String bairro,
                          @NotNull String cidade,
                          @NotNull String numero,
                          @NotNull String estado,
                          UUID id,
                          UUID vendedorId,
                          long version) {
}
