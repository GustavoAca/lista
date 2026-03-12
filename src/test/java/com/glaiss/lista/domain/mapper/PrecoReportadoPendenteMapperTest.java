package com.glaiss.lista.domain.mapper;

import com.glaiss.lista.domain.model.EStatusPrecoReportado;
import com.glaiss.lista.domain.model.ItemOferta;
import com.glaiss.lista.domain.model.PrecoReportadoPendente;
import com.glaiss.lista.domain.model.StatusPrecoReportado;
import com.glaiss.lista.domain.model.dto.PrecoReportadoPendenteDTO;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PrecoReportadoPendenteMapperTest {

    @Mock
    private EntityManager em;

    @InjectMocks
    private PrecoReportadoPendenteMapper mapper;

    @Nested
    class Dado_uma_entidade_preco_reportado_pendente {

        @Test
        void quando_toDto_entao_deve_mapear_campos_corretamente() {
            UUID id = UUID.randomUUID();
            UUID itemOfertaId = UUID.randomUUID();
            PrecoReportadoPendente entity = PrecoReportadoPendente.builder()
                    .id(id)
                    .itemOferta(ItemOferta.builder().id(itemOfertaId).build())
                    .statusPrecoReportado(StatusPrecoReportado.builder().codigo(EStatusPrecoReportado.AGUARDANDO).build())
                    .preco(new BigDecimal("10.00"))
                    .mensagemErro("Erro")
                    .tentativas((short) 1)
                    .hasPromocaoAtiva(true)
                    .build();
            
            PrecoReportadoPendenteDTO result = mapper.toDto(entity);
            
            assertNotNull(result);
            assertEquals(id, result.id());
            assertEquals(itemOfertaId, result.itemOferta());
            assertEquals(new BigDecimal("10.00"), result.preco());
        }
    }

    @Nested
    class Dado_um_preco_reportado_pendente_dto {

        @Test
        void quando_toEntity_entao_deve_usar_entity_manager_para_referencias() {
            UUID itemOfertaId = UUID.randomUUID();
            EStatusPrecoReportado statusId = EStatusPrecoReportado.AGUARDANDO;
            PrecoReportadoPendenteDTO dto = new PrecoReportadoPendenteDTO(
                    UUID.randomUUID(), itemOfertaId, statusId, new BigDecimal("15.00"),
                    null, (short) 0, false, null, null
            );
            
            ItemOferta itemOfertaMock = mock(ItemOferta.class);
            StatusPrecoReportado statusMock = mock(StatusPrecoReportado.class);
            
            when(em.getReference(eq(ItemOferta.class), eq(itemOfertaId))).thenReturn(itemOfertaMock);
            when(em.getReference(eq(StatusPrecoReportado.class), eq(statusId))).thenReturn(statusMock);
            
            PrecoReportadoPendente result = mapper.toEntity(dto);
            
            assertNotNull(result);
            assertEquals(new BigDecimal("15.00"), result.getPreco());
            assertEquals(itemOfertaMock, result.getItemOferta());
            assertEquals(statusMock, result.getStatusPrecoReportado());
        }
    }
}
