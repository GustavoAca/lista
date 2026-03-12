package com.glaiss.lista.controller.itemoferta;

import com.glaiss.core.jpa.test.GlaissWebMvcTest;
import com.glaiss.lista.config.SecurityConfig;
import com.glaiss.lista.controller.ListaControllerTestBase;
import com.glaiss.lista.controller.itemoferta.dto.ItemOfertaDTO;
import com.glaiss.lista.domain.service.itemoferta.ItemOfertaService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@GlaissWebMvcTest(controllers = ItemOfertaController.class,
    excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class),
    properties = {
        "spring.application.name=lista",
        "servico-externos-permitidos=http://localhost:4200",
        "eureka.client.enabled=false"
    }
)
class ItemOfertaControllerTest extends ListaControllerTestBase {

    @MockBean
    private ItemOfertaService itemOfertaService;

    @Test
    @DisplayName("Quando listar, então deve retornar OK")
    void quando_listar_entao_deve_retornar_ok() throws Exception {
        mockMvc.perform(get("/itens-oferta"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Quando criar, então deve retornar Created")
    void quando_criar_entao_deve_retornar_created() throws Exception {
        ItemOfertaDTO dto = new ItemOfertaDTO(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), false, new BigDecimal("10.0"), null, null, 0);

        mockMvc.perform(post("/itens-oferta")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());

        verify(itemOfertaService).salvar(any(ItemOfertaDTO.class));
    }

    @Test
    @DisplayName("Quando listar por item, então deve retornar OK")
    void quando_listar_por_item_entao_deve_retornar_ok() throws Exception {
        UUID itemId = UUID.randomUUID();
        mockMvc.perform(get("/itens-oferta/{itemId}", itemId))
                .andExpect(status().isOk());
    }
}
