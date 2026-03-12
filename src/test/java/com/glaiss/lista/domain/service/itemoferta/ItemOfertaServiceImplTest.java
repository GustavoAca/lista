package com.glaiss.lista.domain.service.itemoferta;

import com.glaiss.core.domain.model.ResponsePage;
import com.glaiss.core.exception.RegistroNaoEncontradoException;
import com.glaiss.lista.controller.itemoferta.dto.ItemOfertaDTO;
import com.glaiss.lista.domain.mapper.ItemOfertaMapper;
import com.glaiss.lista.domain.model.ItemOferta;
import com.glaiss.lista.domain.model.dto.HistoricoPrecoDTO;
import com.glaiss.lista.domain.model.dto.PrecoReportadoPendenteDTO;
import com.glaiss.lista.domain.model.dto.projection.vendedor.ItemOfertaProjection;
import com.glaiss.lista.domain.repository.ItemOfertaRepository;
import com.glaiss.lista.domain.service.historicopreco.HistoricoPrecoService;
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

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemOfertaServiceImplTest {

    @Mock
    private ItemOfertaRepository repo;

    @Mock
    private ItemOfertaMapper itemOfertaMapper;

    @Mock
    private HistoricoPrecoService historicoPrecoService;

    @InjectMocks
    private ItemOfertaServiceImpl service;

    private ItemOfertaDTO createItemOfertaDTO() {
        return new ItemOfertaDTO(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), false, new BigDecimal("10.0"), null, null, 0L);
    }

    @Nested
    class Salvar {
        @Test
        void quando_salvar_dto_entao_deve_retornar_dto_salvo() {
            ItemOfertaDTO dto = createItemOfertaDTO();
            ItemOferta entity = ItemOferta.builder().build();
            
            when(itemOfertaMapper.toEntity(dto)).thenReturn(entity);
            when(repo.save(entity)).thenReturn(entity);
            when(itemOfertaMapper.toDto(entity)).thenReturn(dto);
            
            assertEquals(dto, service.salvar(dto));
        }
    }

    @Nested
    class CalcularValoresItens {
        @Test
        void quando_mapa_vazio_entao_deve_retornar_zero() {
            assertEquals(BigDecimal.ZERO, service.calcularValoresItens(null));
            assertEquals(BigDecimal.ZERO, service.calcularValoresItens(new HashMap<>()));
        }

        @Test
        void quando_itens_validos_entao_deve_retornar_soma() {
            UUID id1 = UUID.randomUUID();
            UUID id2 = UUID.randomUUID();
            Map<UUID, Short> map = new HashMap<>();
            map.put(id1, (short) 2);
            map.put(id2, (short) 3);
            
            ItemOferta o1 = ItemOferta.builder()
                    .id(id1)
                    .preco(new BigDecimal("10.00"))
                    .build();
            
            ItemOferta o2 = ItemOferta.builder()
                    .id(id2)
                    .preco(new BigDecimal("20.00"))
                    .build();
            
            when(repo.findAllById(anySet())).thenReturn(Arrays.asList(o1, o2));
            
            BigDecimal result = service.calcularValoresItens(map);
            
            assertEquals(new BigDecimal("80.00"), result);
        }

        @Test
        void quando_item_faltando_entao_deve_lancar_excecao() {
            Map<UUID, Short> map = new HashMap<>();
            map.put(UUID.randomUUID(), (short) 1);
            
            when(repo.findAllById(anySet())).thenReturn(Collections.emptyList());
            
            assertThrows(RegistroNaoEncontradoException.class, () -> service.calcularValoresItens(map));
        }
    }

    @Nested
    class BuscarValorPorItemOfertaId {
        @Test
        void quando_item_existe_entao_deve_retornar_preco() {
            UUID id = UUID.randomUUID();
            ItemOferta entity = ItemOferta.builder()
                    .preco(new BigDecimal("15.50"))
                    .build();
            
            when(repo.findById(id)).thenReturn(Optional.of(entity));
            
            assertEquals(new BigDecimal("15.50"), service.buscarValorPorItemOfertaId(id));
        }
    }

    @Nested
    class ListarPaginaDTO {
        @Test
        void quando_listar_entao_deve_retornar_pagina_de_dtos() {
            Pageable pageable = PageRequest.of(0, 10);
            Page<ItemOferta> page = new PageImpl<>(Collections.singletonList(ItemOferta.builder().build()), pageable, 1);
            
            when(repo.findAll(pageable)).thenReturn(page);
            when(itemOfertaMapper.toDto(any())).thenReturn(createItemOfertaDTO());
            
            ResponsePage<ItemOfertaDTO> result = service.listarPaginaDTO(pageable);
            
            assertNotNull(result);
            assertEquals(1, result.getContent().size());
        }
    }

    @Nested
    class AtualizarPrecoReportado {
        @Test
        void quando_atualizar_preco_com_promocao_entao_deve_salvar_historico() {
            UUID id = UUID.randomUUID();
            PrecoReportadoPendenteDTO dto = new PrecoReportadoPendenteDTO(id, UUID.randomUUID(), null, new BigDecimal("10.0"), null, (short)0, true, null, null);
            
            ItemOferta item = ItemOferta.builder()
                    .id(id)
                    .preco(new BigDecimal("100.00"))
                    .build();
            
            when(repo.findById(id)).thenReturn(Optional.of(item));
            
            assertTrue(service.atualizarPrecoReportado(id, dto));
            
            verify(historicoPrecoService).salvar(any(HistoricoPrecoDTO.class));
            verify(repo).save(item);
        }
    }
}
