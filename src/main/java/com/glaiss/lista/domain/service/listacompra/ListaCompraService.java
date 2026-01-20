package com.glaiss.lista.domain.service.listacompra;

import com.glaiss.core.domain.model.ResponsePage;
import com.glaiss.core.domain.service.BaseService;
import com.glaiss.lista.controller.listacompra.dto.*;
import com.glaiss.lista.domain.model.ListaCompra;
import com.glaiss.lista.domain.model.dto.projection.listacompra.ItemListaProjection;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface ListaCompraService extends BaseService<ListaCompra, UUID> {
    ResponsePage<ItemListaProjection> listarItensPorListaCompraIdPaginaDTO(Pageable pageable, UUID listId);

    List<ItemListaRequest> adicionarItemLista(UUID listaId, List<ItemAdicionadoRequest> itemDTO);

    void criarLista(ListaCompraRequest listaCompraRequest);

    ResponsePage<ListaCompraRequest> listar(Pageable pageable);

    ListaCompraRequest buscarPorIdDto(UUID listaId);

    Boolean alterarItens(@Valid UUID listaId, @Valid List<ItemAlteradoRequest> itensLista);

    void concluirLista(@Valid ConcluirListaRequestDTO concluirListaRequestDTO);
}
