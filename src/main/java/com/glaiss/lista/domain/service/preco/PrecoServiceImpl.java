package com.glaiss.lista.domain.service.preco;

import com.glaiss.core.domain.service.BaseServiceImpl;
import com.glaiss.lista.domain.model.Preco;
import com.glaiss.lista.domain.repository.preco.PrecoRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PrecoServiceImpl extends BaseServiceImpl<Preco, UUID, PrecoRepository> implements PrecoService {

    protected PrecoServiceImpl(PrecoRepository repo) {
        super(repo);
    }
}
