package com.glaiss.lista.domain.repository;

import com.glaiss.core.domain.repository.BaseRepository;
import com.glaiss.lista.domain.model.ListaCompra;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ListaCompraRepository extends BaseRepository<ListaCompra, UUID> {

    @Override
    @EntityGraph(attributePaths = {"itensLista", "statusLista"})
    Page<ListaCompra> findAll(Pageable pageable);

    @Override
    @EntityGraph(attributePaths = {"itensLista", "statusLista"})
    Optional<ListaCompra> findById(UUID id);

    @EntityGraph(attributePaths = {"itensLista", "statusLista"})
    Page<ListaCompra> findAllByUsuarioId(Pageable pageable, UUID id);
}
