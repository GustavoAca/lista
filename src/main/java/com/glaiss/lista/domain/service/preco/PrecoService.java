package com.glaiss.lista.domain.service.preco;

import com.glaiss.core.domain.service.BaseService;
import com.glaiss.lista.domain.model.Preco;
import com.glaiss.lista.domain.model.dto.PrecoDto;

import java.util.UUID;

public interface PrecoService extends BaseService<Preco, UUID> {
    PrecoDto buscarPorIdDto(UUID id);
}
