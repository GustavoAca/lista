package com.glaiss.lista.domain.repository.preco;

import com.glaiss.core.domain.repository.BaseRepository;
import com.glaiss.lista.domain.model.Preco;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PrecoRepository extends BaseRepository<Preco, UUID> {
    Page<Preco> findByItemId(UUID itemId, Pageable pageable);
}
