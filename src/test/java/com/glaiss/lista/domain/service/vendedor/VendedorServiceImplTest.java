package com.glaiss.lista.domain.service.vendedor;

import com.glaiss.core.domain.model.ResponsePage;
import com.glaiss.core.exception.RegistroNaoEncontradoException;
import com.glaiss.lista.controller.vendedor.dto.EnderecoDTO;
import com.glaiss.lista.controller.vendedor.dto.NovoEnderecoVendedorDTO;
import com.glaiss.lista.controller.vendedor.dto.VendedorDTO;
import com.glaiss.lista.domain.mapper.VendedorMapper;
import com.glaiss.lista.domain.model.Vendedor;
import com.glaiss.lista.domain.repository.VendedorRepository;
import com.glaiss.lista.domain.service.endereco.EnderecoService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VendedorServiceImplTest {

    @Mock
    private VendedorRepository repo;

    @Mock
    private VendedorMapper vendedorMapper;

    @Mock
    private EnderecoService enderecoService;

    @InjectMocks
    private VendedorServiceImpl service;

    private VendedorDTO createVendedorDTO() {
        return new VendedorDTO(UUID.randomUUID(), "Vendedor", Collections.emptyList(), 0L);
    }

    @Test
    void quando_listar_entao_deve_retornar_pagina() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Vendedor> page = new PageImpl<>(Collections.singletonList(Vendedor.builder().build()), pageable, 1);
        when(repo.findAll(pageable)).thenReturn(page);
        when(vendedorMapper.toDto(any())).thenReturn(createVendedorDTO());

        assertNotNull(service.listarPaginaDTO(pageable));
    }

    @Test
    void quando_buscar_por_id_entao_deve_retornar_dto() {
        UUID id = UUID.randomUUID();
        Vendedor vendedor = Vendedor.builder().id(id).build();
        when(repo.findById(id)).thenReturn(Optional.of(vendedor));
        when(vendedorMapper.toDto(vendedor)).thenReturn(createVendedorDTO());
        
        assertNotNull(service.buscaPorId(id));
    }

    @Test
    void quando_salvar_entao_deve_salvar_vendedor_e_enderecos() {
        UUID id = UUID.randomUUID();
        EnderecoDTO end = new EnderecoDTO("c", "cep", "l", "b", "ci", "n", "e", null, null, 0);
        VendedorDTO dto = new VendedorDTO(id, "Vendedor", Collections.singletonList(end), 0);
        Vendedor entity = Vendedor.builder().id(id).build();
        
        when(vendedorMapper.toEntity(dto)).thenReturn(entity);
        when(repo.save(entity)).thenReturn(entity);
        when(vendedorMapper.toDto(entity)).thenReturn(dto);
        
        service.salvar(dto);
        
        verify(repo).save(entity);
        verify(enderecoService).salvar(any(EnderecoDTO.class));
    }

    @Test
    void quando_adicionar_endereco_entao_deve_salvar_no_service() {
        UUID id = UUID.randomUUID();
        EnderecoDTO end = new EnderecoDTO("c", "cep", "l", "b", "ci", "n", "e", null, null, 0);
        NovoEnderecoVendedorDTO dto = new NovoEnderecoVendedorDTO(id, Collections.singletonList(end), 0);
        
        when(repo.findById(id)).thenReturn(Optional.of(Vendedor.builder().build()));
        
        assertTrue(service.adicionarEndereco(dto));
        verify(enderecoService).salvar(any(EnderecoDTO.class));
    }

    @Test
    void quando_buscar_por_nome_entao_deve_retornar_pagina() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Vendedor> page = new PageImpl<>(Collections.emptyList(), pageable, 0);
        when(repo.findByNomeContainingIgnoreCase(pageable, "nome")).thenReturn(page);
        
        assertNotNull(service.buscarPorNomeDto(pageable, "nome"));
    }
}
