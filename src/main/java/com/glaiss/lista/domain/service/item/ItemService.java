package com.glaiss.lista.domain.service.item;

import com.glaiss.core.domain.service.BaseService;
import com.glaiss.lista.domain.model.Item;
import com.glaiss.lista.domain.model.dto.ItemDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.UUID;

public interface ItemService extends BaseService<Item, UUID> {

   ItemDto buscarPorIdDto(UUID id);
   Page<ItemDto> listarPaginadoDto(Pageable pageable);
}
