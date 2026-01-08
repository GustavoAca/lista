package com.glaiss.lista.domain.service.precoreportado;

import com.glaiss.core.domain.model.ResponsePage;
import com.glaiss.core.domain.service.BaseService;
import com.glaiss.lista.domain.model.EStatusPrecoReportado;
import com.glaiss.lista.domain.model.PrecoReportadoPendente;
import com.glaiss.lista.domain.model.dto.PrecoReportadoPendenteDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface PrecoReportadoPendenteService extends BaseService<PrecoReportadoPendente, UUID> {

    void salvar(PrecoReportadoPendenteDTO precoReportadoPendenteDTO);

    void salvarAll(List<PrecoReportadoPendenteDTO> precoReportadoPendenteDTO);

    ResponsePage<PrecoReportadoPendenteDTO> listarPorStatusPrecoReportado(Pageable pageable, List<EStatusPrecoReportado> eStatusPrecoReportados);

    void atualizarTentativas(PrecoReportadoPendenteDTO precoReportadoPendenteDTO);

    void excluir(UUID id);

    void concluirProcessamento(PrecoReportadoPendenteDTO dto);
}
