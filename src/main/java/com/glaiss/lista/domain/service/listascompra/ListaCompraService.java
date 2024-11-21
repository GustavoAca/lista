package com.glaiss.lista.domain.service.listascompra;

import com.glaiss.core.domain.service.BaseService;
import com.glaiss.lista.domain.model.ListaCompra;
import com.glaiss.lista.domain.model.dto.ListaCompraDto;

import java.util.UUID;

public interface ListaCompraService extends BaseService<ListaCompra, UUID> {
    ListaCompraDto buscarListaCompraPorUsuarioId();

    ListaCompraDto salvar(ListaCompraDto listaCompraDto);
}
