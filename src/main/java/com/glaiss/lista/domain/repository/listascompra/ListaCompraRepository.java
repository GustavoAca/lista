package com.glaiss.lista.domain.repository.listascompra;

import com.glaiss.core.domain.repository.BaseRepository;
import com.glaiss.lista.domain.model.ListaCompra;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ListaCompraRepository extends BaseRepository<ListaCompra, UUID> {

    @Query("SELECT lc FROM ListaCompra lc" +
            " WHERE lc.usuarioId = :usuarioId")
    ListaCompra findByUsuarioId(@Param("usuarioId") UUID usuarioId);
}
