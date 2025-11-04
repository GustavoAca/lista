package com.glaiss.lista;

import com.glaiss.core.security.Privilegio;
import com.glaiss.core.utils.SecurityContextUtils;
import com.glaiss.lista.domain.model.ListaCompra;
import com.glaiss.lista.domain.model.dto.ListaCompraDto;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@Component
public class MockFactory {

    public ListaCompra construirListaCompra() {
        return ListaCompra.builder()
                .id(UUID.randomUUID())
                .usuarioId(SecurityContextUtils.getId())
                .valorTotal(BigDecimal.ZERO)
                .build();
    }

    public ListaCompraDto construirListaCompraDto() {
        return ListaCompraDto.builder()
                .id(UUID.randomUUID())
                .usuarioId(SecurityContextUtils.getId())
                .valorTotal(BigDecimal.ZERO)
                .build();
    }
}
