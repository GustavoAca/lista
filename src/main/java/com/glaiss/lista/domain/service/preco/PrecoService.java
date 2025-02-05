package com.glaiss.lista.domain.service.preco;

import com.glaiss.core.domain.model.ResponsePage;
import com.glaiss.core.domain.service.BaseService;
import com.glaiss.lista.domain.model.Preco;
import com.glaiss.lista.domain.model.dto.PrecoDto;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface PrecoService extends BaseService<Preco, UUID> {
    PrecoDto buscarPorIdDto(UUID id);

    ResponsePage<PrecoDto> buscarPrecoPorItemId(UUID itemId, Pageable pageable);

    PrecoDto salvar(PrecoDto p);

    PrecoDto adicionarPreco(PrecoDto precoDto);
}
