package com.glaiss.lista.domain.service.historicopreco;

import com.glaiss.core.domain.service.BaseServiceImpl;
import com.glaiss.lista.domain.mapper.HistoricoPrecoMapper;
import com.glaiss.lista.domain.model.HistoricoPreco;
import com.glaiss.lista.domain.model.ItemOferta;
import com.glaiss.lista.domain.model.dto.HistoricoPrecoDTO;
import com.glaiss.lista.domain.repository.HistoricoPrecoRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class HistoricoPrecoServiceImpl extends BaseServiceImpl<HistoricoPreco, UUID, HistoricoPrecoRepository> implements HistoricoPrecoService {

    private final HistoricoPrecoMapper historicoPrecoMapper;
    private final EntityManager em;

    protected HistoricoPrecoServiceImpl(HistoricoPrecoRepository repo,
                                        HistoricoPrecoMapper historicoPrecoMapper,
                                        EntityManager em) {
        super(repo);
        this.historicoPrecoMapper = historicoPrecoMapper;
        this.em = em;
    }

    @Override
    @Transactional
    public void salvar(HistoricoPrecoDTO historicoPrecoDTO) {
        HistoricoPreco historicoPreco = historicoPrecoMapper.toEntity(historicoPrecoDTO);
        historicoPreco.setItemOferta(em.getReference(ItemOferta.class, historicoPreco.getItemOfertaId()));
        salvar(historicoPreco);
    }
}
