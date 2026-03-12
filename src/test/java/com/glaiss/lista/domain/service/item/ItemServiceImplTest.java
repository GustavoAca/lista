package com.glaiss.lista.domain.service.item;

import com.glaiss.core.domain.model.ResponsePage;
import com.glaiss.core.exception.RegistroNaoEncontradoException;
import com.glaiss.lista.controller.item.dto.ItemDTO;
import com.glaiss.lista.domain.mapper.ItemMapper;
import com.glaiss.lista.domain.model.Item;
import com.glaiss.lista.domain.repository.ItemRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemServiceImplTest {

    @Mock
    private ItemRepository repo;

    @Mock
    private ItemMapper itemMapper;

    @InjectMocks
    private ItemServiceImpl service;

    private ItemDTO createItemDTO() {
        return new ItemDTO(UUID.randomUUID(), true, "Item", "Desc", 0L);
    }

    @Test
    void quando_salvar_entao_deve_retornar_dto() {
        ItemDTO dto = createItemDTO();
        Item entity = Item.builder().build();
        when(itemMapper.toEntity(dto)).thenReturn(entity);
        when(repo.save(entity)).thenReturn(entity);
        when(itemMapper.toDto(entity)).thenReturn(dto);
        
        assertEquals(dto, service.salvar(dto));
        assertTrue(entity.getIsAtivo());
    }

    @Test
    void quando_listar_entao_deve_retornar_pagina() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Item> page = new PageImpl<>(Collections.singletonList(Item.builder().build()), pageable, 1);
        when(repo.findAll(pageable)).thenReturn(page);
        when(itemMapper.toDto(any())).thenReturn(createItemDTO());
        
        assertNotNull(service.listarPaginaDTO(pageable));
    }

    @Test
    void quando_deletar_lista_entao_deve_chamar_repo_para_cada() {
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();
        when(repo.existsById(any())).thenReturn(false);
        
        service.deletar(Arrays.asList(id1, id2));
        
        verify(repo).deleteById(id1);
        verify(repo).deleteById(id2);
    }

    @Test
    void quando_buscar_por_id_entao_deve_retornar_dto() {
        UUID id = UUID.randomUUID();
        Item item = Item.builder().id(id).build();
        when(repo.findById(id)).thenReturn(Optional.of(item));
        when(itemMapper.toDto(item)).thenReturn(createItemDTO());
        
        assertNotNull(service.buscarPorIdDto(id));
    }

    @Test
    void quando_buscar_por_nome_entao_deve_retornar_pagina() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Item> page = new PageImpl<>(Collections.emptyList(), pageable, 0);
        when(repo.findByNomeContainingIgnoreCase(pageable, "nome")).thenReturn(page);
        
        assertNotNull(service.buscarPorNomeDto(pageable, "nome"));
    }
}
