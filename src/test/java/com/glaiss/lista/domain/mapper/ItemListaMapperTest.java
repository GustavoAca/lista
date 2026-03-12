package com.glaiss.lista.domain.mapper;

import com.glaiss.lista.controller.listacompra.dto.ItemListaRequest;
import com.glaiss.lista.domain.model.ItemLista;
import com.glaiss.lista.domain.model.ItemOferta;
import com.glaiss.lista.domain.model.ListaCompra;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class ItemListaMapperTest {

    @Mock
    private ItemOfertaMapper itemOfertaMapper;

    @InjectMocks
    private ItemListaMapper mapper;

    @Nested
    class Dado_um_objeto_item_lista_request {

        private ItemListaRequest request;
        private UUID id;
        private UUID itemOfertaId;
        private UUID listaCompraId;

        @BeforeEach
        void setup() {
            id = UUID.randomUUID();
            itemOfertaId = UUID.randomUUID();
            listaCompraId = UUID.randomUUID();
            request = new ItemListaRequest(id, listaCompraId, itemOfertaId, (short) 5, 1);
        }

        @Nested
        class Quando_executar_toEntity {

            private ItemLista result;

            @BeforeEach
            void setup() {
                result = mapper.toEntity(request);
            }

            @Test
            void entao_deve_retornar_entidade_com_sucesso() {
                assertNotNull(result);
                assertEquals(id, result.getId());
                assertEquals((short) 5, result.getQuantidade());
                assertNotNull(result.getItemOferta());
                assertEquals(itemOfertaId.toString(), result.getItemOferta().getId().toString());
                assertEquals(listaCompraId.toString(), result.getListaCompra().getId().toString());
            }
        }
    }

    @Nested
    class Dado_uma_entidade_item_lista {

        private ItemLista entity;
        private UUID id;

        @BeforeEach
        void setup() {
            id = UUID.randomUUID();
            entity = ItemLista.builder()
                    .id(id)
                    .quantidade((short) 10)
                    .itemOferta(ItemOferta.builder().id(UUID.randomUUID()).build())
                    .listaCompra(ListaCompra.builder().id(UUID.randomUUID()).build())
                    .version(1L)
                    .build();
        }

        @Nested
        class Quando_executar_toDto {

            @Test
            void entao_deve_retornar_dto_com_sucesso() {
                var result = mapper.toDto(entity);
                assertNotNull(result);
                assertEquals(id, result.id());
                assertEquals((short) 10, result.quantidade());
            }
        }
    }
}
