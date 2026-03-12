package com.glaiss.lista.domain.service.historicopreco;

import com.glaiss.lista.domain.mapper.HistoricoPrecoMapper;
import com.glaiss.lista.domain.model.HistoricoPreco;
import com.glaiss.lista.domain.model.ItemOferta;
import com.glaiss.lista.domain.model.dto.HistoricoPrecoDTO;
import com.glaiss.lista.domain.repository.HistoricoPrecoRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HistoricoPrecoServiceImplTest {

    @Mock
    private HistoricoPrecoRepository repo;

    @Mock
    private HistoricoPrecoMapper historicoPrecoMapper;

    @Mock
    private EntityManager em;

    @InjectMocks
    private HistoricoPrecoServiceImpl service;

    @Test
    void quando_salvar_entao_deve_buscar_referencia_e_salvar() {
        UUID itemOfertaId = UUID.randomUUID();
        HistoricoPrecoDTO dto = new HistoricoPrecoDTO(null, itemOfertaId, null, null, 0);
        HistoricoPreco entity = mock(HistoricoPreco.class);
        ItemOferta itemOferta = ItemOferta.builder().build();
        
        when(historicoPrecoMapper.toEntity(dto)).thenReturn(entity);
        when(entity.getItemOfertaId()).thenReturn(itemOfertaId);
        when(em.getReference(eq(ItemOferta.class), eq(itemOfertaId))).thenReturn(itemOferta);
        
        service.salvar(dto);
        
        verify(entity).setItemOferta(itemOferta);
        verify(repo).save(entity);
    }
}
