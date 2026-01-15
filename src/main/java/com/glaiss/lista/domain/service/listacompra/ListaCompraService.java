package com.glaiss.lista.domain.service.listacompra;

import com.glaiss.core.domain.model.ResponsePage;
import com.glaiss.core.domain.service.BaseService;
import com.glaiss.lista.controller.dto.ItemAdicionadoDTO;
import com.glaiss.lista.controller.dto.ItemAlteradoDTO;
import com.glaiss.lista.controller.dto.ItemListaDTO;
import com.glaiss.lista.controller.dto.ListaCompraDTO;
import com.glaiss.lista.domain.model.ListaCompra;
import com.glaiss.lista.domain.model.dto.projection.ItemListaProjection;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface ListaCompraService extends BaseService<ListaCompra, UUID> {
    ResponsePage<ItemListaProjection> listarItensPorListaCompraIdPaginaDTO(Pageable pageable, UUID listId);

    List<ItemListaDTO> adicionarItemLista(UUID listaId, List<ItemAdicionadoDTO> itemDTO);

    void criarLista(ListaCompraDTO listaCompraDTO);

    ResponsePage<ListaCompraDTO> listar(Pageable pageable);

    ListaCompraDTO buscarPorIdDto(UUID listaId);

    Boolean alterarItens(@Valid UUID listaId, @Valid List<ItemAlteradoDTO> itensLista);
}
