package com.glaiss.lista.domain.service.listascompra;

import com.glaiss.core.domain.service.BaseServiceImpl;
import com.glaiss.lista.domain.model.ListaCompra;
import com.glaiss.lista.domain.repository.listascompra.ListaCompraRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ListaCompraServiceImpl extends BaseServiceImpl<ListaCompra, UUID, ListaCompraRepository> implements ListaCompraService {

    protected ListaCompraServiceImpl(ListaCompraRepository repo) {
        super(repo);
    }
}
