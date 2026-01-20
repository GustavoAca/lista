package com.glaiss.lista.domain.service.item;

import com.glaiss.core.domain.model.ResponsePage;
import com.glaiss.core.domain.service.BaseService;
import com.glaiss.lista.controller.item.dto.ItemDTO;
import com.glaiss.lista.domain.model.Item;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface ItemService extends BaseService<Item, UUID> {

    ResponsePage<ItemDTO> listarPaginaDTO(org.springframework.data.domain.Pageable pageable);

    ItemDTO salvar(ItemDTO item);

    void deletar(List<UUID> itensId);

    ItemDTO buscarPorIdDto(UUID id);

    ResponsePage<ItemDTO> buscarPorNomeDto(Pageable pageable, String nome);
}
