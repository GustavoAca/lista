package com.glaiss.lista.controller.item;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.glaiss.core.jpa.test.GlaissWebMvcTest;
import com.glaiss.lista.config.SecurityConfig;
import com.glaiss.lista.controller.item.dto.ItemDTO;
import com.glaiss.lista.controller.vendedor.VendedorController;
import com.glaiss.lista.domain.service.item.ItemService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@GlaissWebMvcTest(controllers = ItemController.class,
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class),
        properties = {
                "spring.application.name=lista",
                "jwt.public.key=classpath:application.yml",
                "servico-externos-permitidos=http://localhost:4200",
                "eureka.client.enabled=false"
        })
class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ItemService itemService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser
    void quando_buscar_por_id_entao_deve_retornar_dto() throws Exception {
        UUID id = UUID.randomUUID();
        when(itemService.buscarPorIdDto(id)).thenReturn(new ItemDTO(id, true, "Item", "Desc", 0L));

        mockMvc.perform(get("/itens/{itemId}", id))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void quando_criar_entao_deve_retornar_created() throws Exception {
        ItemDTO dto = new ItemDTO(null, true, "Item", "Desc", 0L);

        mockMvc.perform(post("/itens")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());

        verify(itemService).salvar(any(ItemDTO.class));
    }

    @Test
    @WithMockUser
    void quando_deletar_entao_deve_retornar_ok() throws Exception {
        List<UUID> ids = Collections.singletonList(UUID.randomUUID());

        mockMvc.perform(delete("/itens")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ids)))
                .andExpect(status().isOk());

        verify(itemService).deletar(anyList());
    }
}
