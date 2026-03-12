package com.glaiss.lista.domain.mapper;

import com.glaiss.lista.domain.model.HistoricoPreco;
import com.glaiss.lista.domain.model.ItemOferta;
import com.glaiss.lista.domain.model.dto.HistoricoPrecoDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class HistoricoPrecoMapperTest {

    private HistoricoPrecoMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new HistoricoPrecoMapper();
    }

    @Nested
    class Dado_uma_entidade_historico_preco {

        private HistoricoPreco entity;
        private UUID id;
        private UUID itemOfertaId;

        @BeforeEach
        void setup() {
            id = UUID.randomUUID();
            itemOfertaId = UUID.randomUUID();
            entity = HistoricoPreco.builder()
                    .id(id)
                    .preco(new BigDecimal("99.90"))
                    .hasPromocaoAtiva(false)
                    .itemOferta(ItemOferta.builder().id(itemOfertaId).build())
                    .version(1L)
                    .build();
        }

        @Nested
        class Quando_executar_toDto {

            private HistoricoPrecoDTO result;

            @BeforeEach
            void setup() {
                result = mapper.toDto(entity);
            }

            @Test
            void entao_deve_retornar_dto_com_sucesso() {
                assertNotNull(result);
                assertEquals(id, result.id());
                assertEquals(itemOfertaId, result.itemOfertaId());
                assertEquals(new BigDecimal("99.90"), result.preco());
                assertEquals(false, result.hasPromocaoAtiva());
                assertEquals(1L, result.version());
            }
        }
    }

    @Nested
    class Dado_um_historico_preco_dto {

        private HistoricoPrecoDTO dto;
        private UUID id;
        private UUID itemOfertaId;

        @BeforeEach
        void setup() {
            id = UUID.randomUUID();
            itemOfertaId = UUID.randomUUID();
            dto = new HistoricoPrecoDTO(id, itemOfertaId, new BigDecimal("85.00"), true, 2L);
        }

        @Nested
        class Quando_executar_toEntity {

            private HistoricoPreco result;

            @BeforeEach
            void setup() {
                result = mapper.toEntity(dto);
            }

            @Test
            void entao_deve_retornar_entidade_com_sucesso() {
                assertNotNull(result);
                assertEquals(id, result.getId());
                assertEquals(itemOfertaId, result.getItemOferta().getId());
                assertEquals(new BigDecimal("85.00"), result.getPreco());
                assertEquals(true, result.getHasPromocaoAtiva());
                assertEquals(2L, result.getVersion());
            }
        }
    }
}
