package com.glaiss.lista.domain.model.dto.projection.vendedor;

import com.glaiss.lista.domain.model.dto.projection.listacompra.ItemProjection;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public interface ItemOfertaProjection {

    UUID getId();

    ItemProjection getItem();

    Boolean getHasPromocaoAtiva();

    BigDecimal getPreco();

    LocalDateTime getDataInicioPromocao();

    LocalDateTime getDataFinalPromocao();

    long getVersion();
}
