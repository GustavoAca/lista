package com.glaiss.lista.domain.service.itemlista;

import com.glaiss.core.domain.model.ResponsePage;
import com.glaiss.core.domain.service.BaseService;
import com.glaiss.lista.controller.dto.ItemAdicionadoDTO;
import com.glaiss.lista.controller.dto.ItemListaDTO;
import com.glaiss.lista.domain.model.ItemLista;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface ItemListaService extends BaseService<ItemLista, UUID> {

    ResponsePage<ItemListaDTO> listarItensPorListaCompraIdPaginaDTO(Pageable pageable, UUID listId);

    List<ItemListaDTO> adicionaLista(UUID listaId, List<ItemAdicionadoDTO> itemDTO);

    void salvarAll(List<ItemLista> itemListas);

    Boolean removerDaLista(UUID listaId, List<UUID> itensLista);
}
