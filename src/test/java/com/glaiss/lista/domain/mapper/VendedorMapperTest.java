package com.glaiss.lista.domain.mapper;

import com.glaiss.lista.controller.vendedor.dto.VendedorDTO;
import com.glaiss.lista.domain.model.Vendedor;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class VendedorMapperTest {

    @Mock
    private EnderecoMapper enderecoMapper;

    @InjectMocks
    private VendedorMapper mapper;

    @Nested
    class Dado_um_vendedor_dto {

        @Test
        void quando_toEntity_entao_deve_mapear_campos_basicos() {
            UUID id = UUID.randomUUID();
            VendedorDTO dto = new VendedorDTO(id, "Supermercado X", Collections.emptyList(), 0L);
            
            Vendedor result = mapper.toEntity(dto);
            
            assertNotNull(result);
            assertEquals(id, result.getId());
            assertEquals("Supermercado X", result.getNome());
        }
    }

    @Nested
    class Dado_uma_entidade_vendedor {

        @Test
        void quando_toDto_entao_deve_mapear_campos_basicos() {
            UUID id = UUID.randomUUID();
            Vendedor entity = Vendedor.builder()
                    .id(id)
                    .nome("Mercado Y")
                    .version(2L)
                    .build();
            
            VendedorDTO result = mapper.toDto(entity);
            
            assertNotNull(result);
            assertEquals(id, result.id());
            assertEquals("Mercado Y", result.nome());
            assertEquals(2L, result.version());
        }
    }
}
