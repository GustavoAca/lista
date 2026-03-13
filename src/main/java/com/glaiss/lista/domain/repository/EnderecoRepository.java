package com.glaiss.lista.domain.repository;

import com.glaiss.core.domain.repository.BaseRepository;
import com.glaiss.lista.domain.model.Endereco;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EnderecoRepository extends BaseRepository<Endereco, UUID> {

    @Override
    @EntityGraph(attributePaths = {"vendedor"})
    Page<Endereco> findAll(Pageable pageable);

    @Override
    @EntityGraph(attributePaths = {"vendedor"})
    Optional<Endereco> findById(UUID id);
}
