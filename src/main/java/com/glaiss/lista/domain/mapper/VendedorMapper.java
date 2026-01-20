package com.glaiss.lista.domain.mapper;

import com.glaiss.lista.controller.vendedor.dto.EnderecoDTO;
import com.glaiss.lista.controller.vendedor.dto.VendedorDTO;
import com.glaiss.lista.domain.model.Endereco;
import com.glaiss.lista.domain.model.Vendedor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Component
public class VendedorMapper {

    private final EnderecoMapper enderecoMapper;

    public VendedorMapper(EnderecoMapper enderecoMapper) {
        this.enderecoMapper = enderecoMapper;
    }

    public Vendedor toEntity(VendedorDTO dto) {
        UUID id = (Objects.isNull(dto.id())) ? null : dto.id();
        return Vendedor.builder()
                .id(id)
                .nome(dto.nome())
                .enderecos(toEnderecosEntity(dto.enderecos()))
                .build();
    }

    public VendedorDTO toDto(Vendedor entity) {
        UUID id = (Objects.isNull(entity.getId())) ? null : entity.getId();
        return new VendedorDTO(
                id,
                entity.getNome(),
                toEnderecosDto(entity.getEnderecos()),
                entity.getVersion());
    }

    private List<Endereco> toEnderecosEntity(List<EnderecoDTO> endereco) {
        if (Objects.isNull(endereco) || endereco.isEmpty()) {
            return null;
        }
        return endereco.stream().map(enderecoMapper::toEntity).toList();
    }

    private List<EnderecoDTO> toEnderecosDto(List<Endereco> enderecoDTOS) {
        if (Objects.isNull(enderecoDTOS) || enderecoDTOS.isEmpty()) {
            return null;
        }
        return enderecoDTOS.stream().map(enderecoMapper::toDto).toList();
    }
}
