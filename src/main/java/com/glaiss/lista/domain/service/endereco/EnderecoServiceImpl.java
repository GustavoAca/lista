package com.glaiss.lista.domain.service.endereco;

import com.glaiss.core.domain.service.BaseServiceImpl;
import com.glaiss.lista.controller.vendedor.dto.EnderecoDTO;
import com.glaiss.lista.domain.mapper.EnderecoMapper;
import com.glaiss.lista.domain.model.Endereco;
import com.glaiss.lista.domain.repository.EnderecoRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class EnderecoServiceImpl extends BaseServiceImpl<Endereco, UUID, EnderecoRepository> implements EnderecoService {

    private final EnderecoMapper enderecoMapper;

    protected EnderecoServiceImpl(EnderecoRepository repo,
                                  EnderecoMapper enderecoMapper) {
        super(repo);
        this.enderecoMapper = enderecoMapper;
    }

    public EnderecoDTO salvar(EnderecoDTO enderecoDTO){
        return enderecoMapper.toDto(salvar(enderecoMapper.toEntity(enderecoDTO)));
    }
}
