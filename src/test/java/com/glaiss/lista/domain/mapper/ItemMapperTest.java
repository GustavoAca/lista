package com.glaiss.lista.domain.mapper;

import com.glaiss.lista.controller.item.dto.ItemDTO;
import com.glaiss.lista.domain.model.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ItemMapperTest {

    private ItemMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new ItemMapper();
    }

    @Nested
    class Dado_um_objeto_item {

        private Item item;
        private UUID id;

        @BeforeEach
        void setup() {
            id = UUID.randomUUID();
            item = Item.builder()
                    .id(id)
                    .nome("Arroz")
                    .descricao("Arroz Branco 5kg")
                    .isAtivo(true)
                    .version(1L)
                    .build();
        }

        @Nested
        class Quando_executar_toDto {

            private ItemDTO result;

            @BeforeEach
            void setup() {
                result = mapper.toDto(item);
            }

            @Test
            void entao_deve_retornar_dto_com_sucesso() {
                assertNotNull(result);
                assertEquals(id, result.id());
                assertEquals("Arroz", result.nome());
                assertEquals("Arroz Branco 5kg", result.descricao());
                assertEquals(true, result.isAtivo());
                assertEquals(1L, result.version());
            }
        }
    }

    @Nested
    class Dado_um_objeto_item_dto {

        private ItemDTO dto;
        private UUID id;

        @BeforeEach
        void setup() {
            id = UUID.randomUUID();
            dto = new ItemDTO(id, true, "Feijão", "Feijão Preto 1kg", 2L);
        }

        @Nested
        class Quando_executar_toEntity {

            private Item result;

            @BeforeEach
            void setup() {
                result = mapper.toEntity(dto);
            }

            @Test
            void entao_deve_retornar_entidade_com_sucesso() {
                assertNotNull(result);
                assertEquals(id, result.getId());
                assertEquals("Feijão", result.getNome());
                assertEquals("Feijão Preto 1kg", result.getDescricao());
                assertEquals(true, result.getIsAtivo());
                assertEquals(2L, result.getVersion());
            }
        }
    }
}
