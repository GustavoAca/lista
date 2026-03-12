package com.glaiss.lista.domain.mapper;

import com.glaiss.lista.controller.vendedor.dto.EnderecoDTO;
import com.glaiss.lista.domain.model.Endereco;
import com.glaiss.lista.domain.model.Vendedor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class EnderecoMapperTest {

    private EnderecoMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new EnderecoMapper();
    }

    @Nested
    class Dado_um_objeto_endereco_dto {

        private EnderecoDTO dto;
        private UUID id;
        private UUID vendedorId;

        @BeforeEach
        void setup() {
            id = UUID.randomUUID();
            vendedorId = UUID.randomUUID();
            dto = new EnderecoDTO("Apto 101", "12345-678", "Rua das Flores", "Bairro Jardim", "São Paulo", "123", "SP", id, vendedorId, 1);
        }

        @Nested
        class Quando_executar_toEntity {

            private Endereco result;

            @BeforeEach
            void setup() {
                result = mapper.toEntity(dto);
            }

            @Test
            void entao_deve_retornar_entidade_com_sucesso() {
                assertNotNull(result);
                assertEquals(id, result.getId());
                assertEquals("Apto 101", result.getComplemento());
                assertEquals("12345-678", result.getCep());
                assertEquals("Rua das Flores", result.getLogradouro());
                assertEquals(vendedorId, result.getVendedor().getId());
                assertEquals(1, result.getVersion());
            }
        }
    }

    @Nested
    class Dado_uma_entidade_endereco {

        private Endereco entity;
        private UUID id;
        private UUID vendedorId;

        @BeforeEach
        void setup() {
            id = UUID.randomUUID();
            vendedorId = UUID.randomUUID();
            entity = Endereco.builder()
                    .id(id)
                    .complemento("Casa 2")
                    .cep("87654-321")
                    .logradouro("Av. Principal")
                    .bairro("Centro")
                    .cidade("Curitiba")
                    .numero("500")
                    .estado("PR")
                    .vendedor(Vendedor.builder().id(vendedorId).build())
                    .version(2L)
                    .build();
        }

        @Nested
        class Quando_executar_toDto {

            private EnderecoDTO result;

            @BeforeEach
            void setup() {
                result = mapper.toDto(entity);
            }

            @Test
            void entao_deve_retornar_dto_com_sucesso() {
                assertNotNull(result);
                assertEquals(id, result.id());
                assertEquals("Casa 2", result.complemento());
                assertEquals("87654-321", result.cep());
                assertEquals(vendedorId, result.vendedorId());
                assertEquals(2, result.version());
            }
        }
    }
}
