package com.glaiss.lista.domain.model.dto.projection.listacompra;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public interface ItemOfertaProjection {

    UUID getId();

    ItemProjection getItem();

    VendedorProjection getVendedor();

    Boolean getHasPromocaoAtiva();

    BigDecimal getPreco();

    LocalDateTime getDataInicioPromocao();

    LocalDateTime getDataFinalPromocao();

    long getVersion();
}
