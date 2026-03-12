package com.glaiss.lista.domain.service.precoreportado;

import com.glaiss.core.domain.model.ResponsePage;
import com.glaiss.core.exception.RegistroNaoEncontradoException;
import com.glaiss.lista.domain.mapper.PrecoReportadoPendenteMapper;
import com.glaiss.lista.domain.model.EStatusPrecoReportado;
import com.glaiss.lista.domain.model.PrecoReportadoPendente;
import com.glaiss.lista.domain.model.StatusPrecoReportado;
import com.glaiss.lista.domain.model.dto.PrecoReportadoPendenteDTO;
import com.glaiss.lista.domain.repository.PrecoReportadoPendenteRepository;
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
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PrecoReportadoPendenteServiceImplTest {

    @Mock
    private PrecoReportadoPendenteRepository repo;

    @Mock
    private PrecoReportadoPendenteMapper precoReportadoPendenteMapper;

    @InjectMocks
    private PrecoReportadoPendenteServiceImpl service;

    private PrecoReportadoPendenteDTO createDTO() {
        return new PrecoReportadoPendenteDTO(UUID.randomUUID(), UUID.randomUUID(), EStatusPrecoReportado.AGUARDANDO, BigDecimal.ZERO, null, (short)0, false, null, null);
    }

    @Test
    void quando_salvar_entao_deve_chamar_repo() {
        PrecoReportadoPendenteDTO dto = createDTO();
        PrecoReportadoPendente entity = PrecoReportadoPendente.builder().build();
        when(precoReportadoPendenteMapper.toEntity(dto)).thenReturn(entity);
        
        service.salvar(dto);
        
        verify(repo).save(entity);
    }

    @Test
    void quando_salvar_todos_entao_deve_chamar_repo() {
        PrecoReportadoPendenteDTO dto = createDTO();
        PrecoReportadoPendente entity = PrecoReportadoPendente.builder().build();
        when(precoReportadoPendenteMapper.toEntity(dto)).thenReturn(entity);
        
        service.salvarAll(Collections.singletonList(dto));
        
        verify(repo).saveAll(anyList());
    }

    @Test
    void quando_listar_por_status_entao_deve_retornar_pagina() {
        Pageable pageable = PageRequest.of(0, 10);
        List<EStatusPrecoReportado> status = Collections.singletonList(EStatusPrecoReportado.AGUARDANDO);
        Page<PrecoReportadoPendente> page = new PageImpl<>(Collections.emptyList(), pageable, 0);
        
        when(repo.findAllByStatusPrecoReportado_CodigoIn(pageable, status)).thenReturn(page);
        
        ResponsePage<PrecoReportadoPendenteDTO> result = service.listarPorStatusPrecoReportado(pageable, status);
        
        assertNotNull(result);
    }

    @Test
    void quando_atualizar_tentativas_entao_deve_incrementar_e_salvar() {
        UUID id = UUID.randomUUID();
        PrecoReportadoPendenteDTO dto = new PrecoReportadoPendenteDTO(id, UUID.randomUUID(), EStatusPrecoReportado.AGUARDANDO, BigDecimal.ZERO, "erro", (short) 0, false, null, null);
        PrecoReportadoPendente entity = PrecoReportadoPendente.builder()
                .id(id)
                .tentativas((short) 1)
                .build();
        
        when(repo.findById(id)).thenReturn(Optional.of(entity));
        
        service.atualizarTentativas(dto);
        
        assertEquals((short) 2, entity.getTentativas());
        assertEquals("erro", entity.getMensagemErro());
        verify(repo).save(entity);
    }

    @Test
    void quando_concluir_processamento_entao_deve_mudar_status() {
        UUID id = UUID.randomUUID();
        PrecoReportadoPendenteDTO dto = new PrecoReportadoPendenteDTO(id, UUID.randomUUID(), EStatusPrecoReportado.AGUARDANDO, BigDecimal.ZERO, null, (short) 0, false, null, null);
        StatusPrecoReportado status = StatusPrecoReportado.builder().build();
        PrecoReportadoPendente entity = PrecoReportadoPendente.builder()
                .id(id)
                .statusPrecoReportado(status)
                .build();
        
        when(repo.findById(id)).thenReturn(Optional.of(entity));
        
        service.concluirProcessamento(dto);
        
        assertEquals(EStatusPrecoReportado.FINALIZADO, status.getCodigo());
        verify(repo).save(entity);
    }
}
