package com.glaiss.lista.domain.repository;

import com.glaiss.core.domain.repository.BaseRepository;
import com.glaiss.lista.domain.model.HistoricoPreco;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface HistoricoPrecoRepository extends BaseRepository<HistoricoPreco, UUID> {
}
