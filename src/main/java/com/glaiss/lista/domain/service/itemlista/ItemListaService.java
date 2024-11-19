package com.glaiss.lista.domain.service.itemlista;

import com.glaiss.core.domain.repository.BaseRepository;
import com.glaiss.core.domain.service.BaseService;
import com.glaiss.lista.domain.model.ItemLista;
import org.springframework.stereotype.Repository;

import java.util.UUID;


public interface ItemListaService extends BaseService<ItemLista, UUID> {
}
