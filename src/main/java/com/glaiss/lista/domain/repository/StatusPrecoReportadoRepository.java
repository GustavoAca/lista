package com.glaiss.lista.domain.repository;

import com.glaiss.core.domain.repository.BaseRepository;
import com.glaiss.lista.domain.model.StatusPrecoReportado;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface StatusPrecoReportadoRepository extends BaseRepository<StatusPrecoReportado, UUID> {
}
