package com.glaiss.lista.domain.repository.itemlista;

import com.glaiss.core.domain.repository.BaseRepository;
import com.glaiss.lista.domain.model.ItemLista;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ItemListaRepository extends BaseRepository<ItemLista, UUID> {

    Page<ItemLista> findByListaCompra(UUID listaCompraId, Pageable pageable);
}
