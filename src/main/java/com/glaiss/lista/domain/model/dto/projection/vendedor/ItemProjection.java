package com.glaiss.lista.domain.model.dto.projection.vendedor;

import java.util.UUID;

public interface ItemProjection {
    UUID getId();

    Boolean getIsAtivo();

    String getNome();

    String getDescricao();
}
