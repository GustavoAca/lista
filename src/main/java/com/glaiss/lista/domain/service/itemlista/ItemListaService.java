package com.glaiss.lista.domain.service.itemlista;

import com.glaiss.core.domain.model.ResponsePage;
import com.glaiss.core.domain.service.BaseService;
import com.glaiss.lista.controller.listacompra.dto.ItemAdicionadoRequest;
import com.glaiss.lista.controller.listacompra.dto.ItemAlteradoRequest;
import com.glaiss.lista.controller.listacompra.dto.ItemListaRequest;
import com.glaiss.lista.domain.model.ItemLista;
import com.glaiss.lista.domain.model.dto.projection.listacompra.ItemListaProjection;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface ItemListaService extends BaseService<ItemLista, UUID> {

    ResponsePage<ItemListaProjection> listarItensPorListaCompraIdPaginaDTO(Pageable pageable, UUID listId);

    List<ItemListaRequest> adicionaLista(UUID listaId, List<ItemAdicionadoRequest> itemDTO);

    void salvarAll(List<ItemLista> itemListas);

    List<ItemLista> buscarTodosPorLista(UUID listaId);

    Boolean alterarItens(UUID listaId, List<ItemAlteradoRequest> itensLista);

    void salvarAllConcluindoLista(List<ItemLista> itensLista);
}
