package com.glaiss.lista.domain.service.listascompra;

import com.glaiss.core.domain.model.ResponsePage;
import com.glaiss.core.domain.service.BaseService;
import com.glaiss.lista.domain.model.ListaCompra;
import com.glaiss.lista.domain.model.dto.ItemListaDto;
import com.glaiss.lista.domain.model.dto.ListaCompraDto;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface ListaCompraService extends BaseService<ListaCompra, UUID> {

    ListaCompraDto salvar(@Valid List<ItemListaDto> itens);

    ResponsePage<ListaCompraDto> listarPaginaDto(Pageable pageable);

    ListaCompraDto buscarPorIdDto(UUID id);

    ListaCompraDto atualizarValorTotal(ListaCompraDto listaCompraDto);
}
