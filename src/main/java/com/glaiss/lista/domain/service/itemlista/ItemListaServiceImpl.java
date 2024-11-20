package com.glaiss.lista.domain.service.itemlista;

import com.glaiss.core.domain.service.BaseServiceImpl;
import com.glaiss.lista.domain.model.ItemLista;
import com.glaiss.lista.domain.repository.itemlista.ItemListaRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ItemListaServiceImpl extends BaseServiceImpl<ItemLista, UUID, ItemListaRepository> implements ItemListaService {

    protected ItemListaServiceImpl(ItemListaRepository repo) {
        super(repo);
    }
}
