package com.glaiss.lista.domain.service.preco;

import com.glaiss.core.domain.service.BaseServiceImpl;
import com.glaiss.core.exception.RegistroNaoEncontradoException;
import com.glaiss.lista.domain.mapper.PrecoMapper;
import com.glaiss.lista.domain.model.Item;
import com.glaiss.lista.domain.model.Preco;
import com.glaiss.lista.domain.model.dto.PrecoDto;
import com.glaiss.lista.domain.repository.preco.PrecoRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PrecoServiceImpl extends BaseServiceImpl<Preco, UUID, PrecoRepository> implements PrecoService {

    private final PrecoMapper precoMapper;

    protected PrecoServiceImpl(PrecoRepository repo,
                               PrecoMapper precoMapper) {
        super(repo);
        this.precoMapper = precoMapper;
    }

    @Override
    public PrecoDto buscarPorIdDto(UUID id) {
        return precoMapper.toDto(buscarPorId(id)
                .orElseThrow(() -> new RegistroNaoEncontradoException(id, Item.class.getName())));
    }
}
