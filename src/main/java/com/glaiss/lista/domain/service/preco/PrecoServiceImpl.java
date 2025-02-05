package com.glaiss.lista.domain.service.preco;

import com.glaiss.core.domain.model.ResponsePage;
import com.glaiss.core.domain.service.BaseServiceImpl;
import com.glaiss.core.exception.RegistroNaoEncontradoException;
import com.glaiss.lista.domain.mapper.PrecoMapper;
import com.glaiss.lista.domain.model.Item;
import com.glaiss.lista.domain.model.Preco;
import com.glaiss.lista.domain.model.dto.PrecoDto;
import com.glaiss.lista.domain.repository.preco.PrecoRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
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

    @Override
    public ResponsePage<PrecoDto> buscarPrecoPorItemId(UUID itemId, Pageable pageable) {
        var precosPaginado = this.repo.findByItemId(itemId, pageable);
        LinkedList<PrecoDto> precos = new LinkedList<>(precosPaginado
                .getContent().stream().map(precoMapper::toDto).toList());
        return new ResponsePage<>(precos, pageable.getPageNumber(), pageable.getPageSize(), precosPaginado.getTotalElements());
    }

    @Override
    public PrecoDto salvar(PrecoDto precoDto) {
        return precoMapper.toDto(salvar(precoMapper.toEntity(precoDto)));
    }

    @Override
    public PrecoDto adicionarPreco(PrecoDto precoDto) {
        return precoMapper.toDto(salvar(precoMapper.toEntity(precoDto)));
    }
}
