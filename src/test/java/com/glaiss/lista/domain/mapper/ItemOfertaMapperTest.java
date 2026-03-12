package com.glaiss.lista.domain.mapper;

import com.glaiss.lista.controller.itemoferta.dto.ItemOfertaDTO;
import com.glaiss.lista.domain.model.ItemOferta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ItemOfertaMapperTest {

    private ItemOfertaMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new ItemOfertaMapper();
    }

    @Nested
    class Dado_um_objeto_item_oferta {

        private ItemOferta entity;
        private UUID id;

        @BeforeEach
        void setup() {
            id = UUID.randomUUID();
            entity = ItemOferta.builder()
                    .id(id)
                    .preco(new BigDecimal("25.90"))
                    .hasPromocaoAtiva(true)
                    .version(1L)
                    .build();
        }

        @Nested
        class Quando_executar_toDto {

            private ItemOfertaDTO result;

            @BeforeEach
            void setup() {
                result = mapper.toDto(entity);
            }

            @Test
            void entao_deve_retornar_dto_com_sucesso() {
                assertNotNull(result);
                assertEquals(id, result.id());
                assertEquals(new BigDecimal("25.90"), result.preco());
                assertEquals(true, result.hasPromocaoAtiva());
                assertEquals(1L, result.version());
            }
        }
    }

    @Nested
    class Dado_um_objeto_item_oferta_dto {

        private ItemOfertaDTO dto;
        private UUID id;

        @BeforeEach
        void setup() {
            id = UUID.randomUUID();
            dto = new ItemOfertaDTO(id, UUID.randomUUID(), UUID.randomUUID(), false, new BigDecimal("15.00"), null, null, 2L);
        }

        @Nested
        class Quando_executar_toEntity {

            private ItemOferta result;

            @BeforeEach
            void setup() {
                result = mapper.toEntity(dto);
            }

            @Test
            void entao_deve_retornar_entidade_com_sucesso() {
                assertNotNull(result);
                assertEquals(id, result.getId());
                assertEquals(new BigDecimal("15.00"), result.getPreco());
                assertEquals(false, result.getHasPromocaoAtiva());
                assertEquals(2L, result.getVersion());
            }
        }
    }
}
