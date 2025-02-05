package com.glaiss.lista.domain.service.itemlista;

import com.glaiss.core.domain.model.ResponsePage;
import com.glaiss.core.domain.service.BaseService;
import com.glaiss.lista.domain.model.ItemLista;
import com.glaiss.lista.domain.model.dto.ItemListaDto;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;


public interface ItemListaService extends BaseService<ItemLista, UUID> {

    ResponsePage<ItemListaDto> buscarItensListaPorListaCompraId(UUID compraId, Pageable pageable);

    Boolean removerItemLista(UUID id);

    Boolean adicionarItens(UUID localId, List<ItemListaDto> itensLista);

    ResponsePage<ItemListaDto> listarPaginadoDto(Pageable pageable);

    ItemListaDto buscarPorIdDto(UUID id);
}
