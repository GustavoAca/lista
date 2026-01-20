package com.glaiss.lista.domain.model.dto.projection.listacompra;

import java.util.UUID;

public interface ItemProjection {

    UUID getId();

    Boolean getIsAtivo();

    String getNome();

    String getDescricao();

    long getVersion();
}
