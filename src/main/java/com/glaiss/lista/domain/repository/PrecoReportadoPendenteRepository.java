package com.glaiss.lista.domain.repository;

import com.glaiss.core.domain.repository.BaseRepository;
import com.glaiss.lista.domain.model.EStatusPrecoReportado;
import com.glaiss.lista.domain.model.PrecoReportadoPendente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PrecoReportadoPendenteRepository extends BaseRepository<PrecoReportadoPendente, UUID> {
    Page<PrecoReportadoPendente> findAllByStatusPrecoReportado_CodigoIn(Pageable pageable, List<EStatusPrecoReportado> statusPrecoReportadoS);
}
