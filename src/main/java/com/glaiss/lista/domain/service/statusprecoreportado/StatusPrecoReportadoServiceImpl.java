package com.glaiss.lista.domain.service.statusprecoreportado;

import com.glaiss.core.domain.service.BaseServiceImpl;
import com.glaiss.lista.domain.model.StatusPrecoReportado;
import com.glaiss.lista.domain.repository.StatusPrecoReportadoRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class StatusPrecoReportadoServiceImpl extends BaseServiceImpl<StatusPrecoReportado, UUID, StatusPrecoReportadoRepository> implements StatusPrecoReportadoService {

    protected StatusPrecoReportadoServiceImpl(StatusPrecoReportadoRepository repo) {
        super(repo);
    }
}
