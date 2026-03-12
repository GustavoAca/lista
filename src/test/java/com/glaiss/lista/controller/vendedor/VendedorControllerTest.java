package com.glaiss.lista.controller.vendedor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.glaiss.core.jpa.test.GlaissWebMvcTest;
import com.glaiss.lista.config.SecurityConfig;
import com.glaiss.lista.controller.itemoferta.ItemOfertaController;
import com.glaiss.lista.controller.vendedor.dto.EnderecoDTO;
import com.glaiss.lista.controller.vendedor.dto.NovoEnderecoVendedorDTO;
import com.glaiss.lista.controller.vendedor.dto.VendedorDTO;
import com.glaiss.lista.domain.service.vendedor.VendedorService;
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
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@GlaissWebMvcTest(controllers = VendedorController.class,
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class),
        properties = {
                "spring.application.name=lista",
                "jwt.public.key=classpath:application.yml",
                "servico-externos-permitidos=http://localhost:4200",
                "eureka.client.enabled=false"
        })
class VendedorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VendedorService vendedorService;

    @Autowired
    private ObjectMapper objectMapper;

    private VendedorDTO createVendedorDTO() {
        return new VendedorDTO(UUID.randomUUID(), "Vendedor", Collections.emptyList(), 0L);
    }

    @Test
    @WithMockUser
    void quando_paginar_entao_deve_retornar_ok() throws Exception {
        mockMvc.perform(get("/vendedores"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void quando_criar_entao_deve_retornar_created() throws Exception {
        VendedorDTO dto = createVendedorDTO();

        mockMvc.perform(post("/vendedores")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());

        verify(vendedorService).salvar(any(VendedorDTO.class));
    }

    @Test
    @WithMockUser
    void quando_adicionar_endereco_entao_deve_retornar_ok() throws Exception {
        List<EnderecoDTO> enderecoDTOList = new LinkedList<>();
        EnderecoDTO enderecoDTO = new EnderecoDTO( "Complemento", "09123", "Rua A", "123", "Bairro B", "Cidade C", "Estado D", UUID.randomUUID(), UUID.randomUUID(), 1L);
        enderecoDTOList.add(enderecoDTO);
        NovoEnderecoVendedorDTO dto = new NovoEnderecoVendedorDTO(UUID.randomUUID(), enderecoDTOList, 0L);

        mockMvc.perform(post("/vendedores/adicionar-endereco")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());

        verify(vendedorService).adicionarEndereco(any());
    }

    @Test
    @WithMockUser
    void quando_buscar_por_id_entao_deve_retornar_dto() throws Exception {
        UUID id = UUID.randomUUID();
        when(vendedorService.buscarPorIdDto(id)).thenReturn(createVendedorDTO());

        mockMvc.perform(get("/vendedores/{vendedorId}", id))
                .andExpect(status().isOk());
    }
}
