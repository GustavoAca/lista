package com.glaiss.lista.domain.mapper;

import com.glaiss.lista.controller.dto.EnderecoDTO;
import com.glaiss.lista.domain.model.Endereco;
import com.glaiss.lista.domain.model.Vendedor;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.UUID;

@Component
public class EnderecoMapper {

    public Endereco toEntity(EnderecoDTO dto) {
        UUID enderecoId = Objects.isNull(dto.id()) ? null : dto.id();
        UUID vendedorId = Objects.isNull(dto.vendedorId()) ? null : dto.vendedorId();
        return Endereco.builder()
                .id(enderecoId)
                .complemento(dto.complemento())
                .cep(dto.cep())
                .logradouro(dto.logradouro())
                .bairro(dto.bairro())
                .cidade(dto.cidade())
                .numero(dto.numero())
                .estado(dto.estado())
                .vendedor(Vendedor.builder().id(vendedorId).build())
                .build();
    }

    public EnderecoDTO toDto(Endereco entity) {
        UUID enderecoId = Objects.isNull(entity.getId()) ? null : entity.getId();
        Vendedor vendedor = Objects.isNull(entity.getVendedor()) ? null : entity.getVendedor();
        UUID vendedorId = Objects.isNull(vendedor.getId()) ? null : vendedor.getId();
        return new EnderecoDTO(entity.getComplemento(),
                entity.getCep(),
                entity.getLogradouro(),
                entity.getBairro(),
                entity.getCidade(),
                entity.getNumero(),
                entity.getEstado(),
                enderecoId,
                vendedorId
        );
    }
}
