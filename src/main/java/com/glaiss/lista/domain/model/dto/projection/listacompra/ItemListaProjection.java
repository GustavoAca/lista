package com.glaiss.lista.domain.model.dto.projection.listacompra;

import java.math.BigDecimal;
import java.util.UUID;

public interface ItemListaProjection {
    UUID getId();

    BigDecimal getPrecoUnitario();

    UUID getListaCompraId();

    ItemOfertaProjection getItemOferta();

    short getQuantidade();

    long getVersion();
}
