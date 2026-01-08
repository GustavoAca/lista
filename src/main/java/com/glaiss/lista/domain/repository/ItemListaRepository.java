package com.glaiss.lista.domain.repository;

import com.glaiss.core.domain.repository.BaseRepository;
import com.glaiss.lista.domain.model.ItemLista;
import feign.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ItemListaRepository extends BaseRepository<ItemLista, UUID> {

    Page<ItemLista> findAllByListaCompraId(Pageable pageable, UUID listaCompraId);


    @Modifying(clearAutomatically = true)
    @Query("""
        delete from ItemLista i
        where i.listaCompra = :listaId
          and i.id in :itensIds
    """)
    int deleteItensDaLista(
            @Param("listaId") UUID listaId,
            @Param("itensIds") List<UUID> itensIds
    );
}
