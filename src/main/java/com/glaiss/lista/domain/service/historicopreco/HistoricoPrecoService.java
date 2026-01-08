package com.glaiss.lista.domain.service.historicopreco;

import com.glaiss.core.domain.service.BaseService;
import com.glaiss.lista.domain.model.HistoricoPreco;
import com.glaiss.lista.domain.model.dto.HistoricoPrecoDTO;

import java.util.UUID;

public interface HistoricoPrecoService extends BaseService<HistoricoPreco, UUID> {

    void salvar(HistoricoPrecoDTO historicoPrecoDTO);
}
