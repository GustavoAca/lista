package com.glaiss.lista.domain.service.historicopreco;

import com.glaiss.core.domain.service.BaseServiceImpl;
import com.glaiss.lista.domain.mapper.HistoricoPrecoMapper;
import com.glaiss.lista.domain.model.HistoricoPreco;
import com.glaiss.lista.domain.model.dto.HistoricoPrecoDTO;
import com.glaiss.lista.domain.repository.HistoricoPrecoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class HistoricoPrecoServiceImpl extends BaseServiceImpl<HistoricoPreco, UUID, HistoricoPrecoRepository> implements HistoricoPrecoService {

    private final HistoricoPrecoMapper historicoPrecoMapper;

    protected HistoricoPrecoServiceImpl(HistoricoPrecoRepository repo,
                                        HistoricoPrecoMapper historicoPrecoMapper) {
        super(repo);
        this.historicoPrecoMapper = historicoPrecoMapper;
    }

    @Override
    @Transactional
    public void salvar(HistoricoPrecoDTO historicoPrecoDTO) {
        salvar(historicoPrecoMapper.toEntity(historicoPrecoDTO));
    }
}
