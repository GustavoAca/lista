package com.glaiss.lista.controller.listacompra;

import com.glaiss.core.jpa.test.GlaissWebMvcTest;
import com.glaiss.lista.config.SecurityConfig;
import com.glaiss.lista.controller.ListaControllerTestBase;
import com.glaiss.lista.controller.listacompra.dto.ListaCompraRequest;
import com.glaiss.lista.domain.model.EStatusLista;
import com.glaiss.lista.domain.model.ListaCompra;
import com.glaiss.lista.domain.service.listacompra.ListaCompraService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@GlaissWebMvcTest(controllers = ListaCompraController.class,
    excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class),
    properties = {
        "spring.application.name=lista",
        "servico-externos-permitidos=http://localhost:4200",
        "eureka.client.enabled=false"
    }
)
class ListaCompraControllerTest extends ListaControllerTestBase {

    @MockBean
    private ListaCompraService listaCompraService;

    @Nested
    @DisplayName("Testes para ListaCompraController")
    class ListaCompraEndpoints {

        @Test
        @DisplayName("Dado uma nova lista, quando criar, então deve retornar 201")
        void dadoNovaLista_quandoCriar_entaoDeveRetornar201() throws Exception {
            ListaCompraRequest dto = new ListaCompraRequest(
                    UUID.randomUUID(), 
                    UUID.randomUUID(),
                    "Minha Lista", 
                    BigDecimal.ZERO,
                    (short) 0, 
                    Collections.emptyList(), 
                    0L, 
                    EStatusLista.AGUARDANDO, 
                    LocalDateTime.now(), 
                    LocalDateTime.now()
            );
            
            ListaCompra lista = ListaCompra.builder()
                    .id(UUID.randomUUID())
                    .nome(dto.nome())
                    .usuarioId(dto.usuarioId())
                    .build();

            mockMvc.perform(post("/listas-compras")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(dto)))
                    .andExpect(status().isCreated());
        }

        @Test
        @DisplayName("Dado um ID válido, quando buscar por ID, então deve retornar 200")
        void dadoIdValido_quandoBuscarPorId_entaoDeveRetornar200() throws Exception {
            UUID id = UUID.randomUUID();
            ListaCompraRequest dto = new ListaCompraRequest(
                    id, 
                    UUID.randomUUID(),
                    "Lista Teste", 
                    BigDecimal.ZERO,
                    (short) 0, 
                    Collections.emptyList(), 
                    0L, 
                    EStatusLista.AGUARDANDO, 
                    LocalDateTime.now(), 
                    LocalDateTime.now()
            );

            when(listaCompraService.buscarPorIdDto(id)).thenReturn(dto);

            mockMvc.perform(get("/listas-compras/{id}", id))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(id.toString()));
        }
    }
}
