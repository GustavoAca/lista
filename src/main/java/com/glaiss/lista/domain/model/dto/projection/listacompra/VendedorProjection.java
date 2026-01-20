package com.glaiss.lista.domain.model.dto.projection.listacompra;

import java.util.UUID;

public interface VendedorProjection {

    UUID getId();

    String getNome();

    long getVersion();
}
