package com.glaiss.lista.domain.mapper;

import com.glaiss.lista.controller.listacompra.dto.ListaCompraRequest;
import com.glaiss.lista.domain.model.EStatusLista;
import com.glaiss.lista.domain.model.ListaCompra;
import com.glaiss.lista.domain.model.StatusLista;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class ListaCompraMapperTest {

    @Mock
    private ItemListaMapper itemListaMapper;

    @InjectMocks
    private ListaCompraMapper mapper;

    @Nested
    class Dado_um_dto_de_requisicao {

        @Test
        void quando_toEntity_entao_deve_mapear_campos_corretamente() {
            UUID id = UUID.randomUUID();
            UUID usuarioId = UUID.randomUUID();
            ListaCompraRequest dto = new ListaCompraRequest(
                    id, usuarioId, "Minha Lista", new BigDecimal("50.00"), 
                    (short) 5, Collections.emptyList(), 1L, EStatusLista.AGUARDANDO, null, null
            );
            
            ListaCompra result = mapper.toEntity(dto);
            
            assertNotNull(result);
            assertEquals(id, result.getId());
            assertEquals(usuarioId, result.getUsuarioId());
            assertEquals("Minha Lista", result.getNome());
            assertEquals(new BigDecimal("50.00"), result.getValorTotal());
            assertEquals(EStatusLista.AGUARDANDO, result.getStatusLista().getCodigo());
        }
    }

    @Nested
    class Dado_uma_entidade_lista_compra {

        @Test
        void quando_toDto_entao_deve_mapear_campos_corretamente() {
            UUID id = UUID.randomUUID();
            ListaCompra entity = ListaCompra.builder()
                    .id(id)
                    .nome("Lista Mensal")
                    .valorTotal(new BigDecimal("150.00"))
                    .totalItens((short) 10)
                    .statusLista(StatusLista.builder().codigo(EStatusLista.FINALIZADA).build())
                    .version(1L)
                    .build();
            
            ListaCompraRequest result = mapper.toDto(entity);
            
            assertNotNull(result);
            assertEquals(id, result.id());
            assertEquals("Lista Mensal", result.nome());
            assertEquals(new BigDecimal("150.00"), result.valorTotal());
            assertEquals(EStatusLista.FINALIZADA, result.statusLista());
        }
    }
}
