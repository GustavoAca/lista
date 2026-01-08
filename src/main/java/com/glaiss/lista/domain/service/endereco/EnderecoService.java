package com.glaiss.lista.domain.service.endereco;

import com.glaiss.core.domain.service.BaseService;
import com.glaiss.lista.controller.dto.EnderecoDTO;
import com.glaiss.lista.domain.model.Endereco;

import java.util.UUID;

public interface EnderecoService extends BaseService<Endereco, UUID> {

    EnderecoDTO salvar(EnderecoDTO enderecoDTO);
}
