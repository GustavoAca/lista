package com.glaiss.lista.domain.model.dto.projection;

import java.util.UUID;

public interface ItemListaProjection {
    UUID getId();

    UUID getListaCompraId();

    ItemOfertaProjection getItemOferta();

    short getQuantidade();
}
