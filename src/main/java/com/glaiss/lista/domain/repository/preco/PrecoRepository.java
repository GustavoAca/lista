package com.glaiss.lista.domain.repository.preco;

import com.glaiss.core.domain.repository.BaseRepository;
import com.glaiss.lista.domain.model.Preco;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PrecoRepository extends BaseRepository<Preco, UUID> {
}
