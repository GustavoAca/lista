package com.glaiss.lista.domain.repository;

import com.glaiss.core.domain.repository.BaseRepository;
import com.glaiss.lista.domain.model.ItemLista;
import com.glaiss.lista.domain.model.dto.projection.listacompra.ItemListaProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ItemListaRepository extends BaseRepository<ItemLista, UUID> {

    @Query(value = "SELECT i FROM ItemLista i " +
            "JOIN FETCH i.itemOferta io " +
            "JOIN FETCH io.item " +
            "WHERE i.listaCompra.id = :listaCompraId",
            countQuery = "SELECT count(i) FROM ItemLista i WHERE i.listaCompra.id = :listaCompraId")
    Page<ItemListaProjection> findAllByListaCompra_Id(Pageable pageable, @Param("listaCompraId") UUID listaCompraId);

    @Query("SELECT i FROM ItemLista i JOIN FETCH i.itemOferta WHERE i.listaCompra.id = :listaId")
    List<ItemLista> findAllByListaCompraId(@Param("listaId") UUID listaId);

    @Modifying(clearAutomatically = true)
    @Query("""
                delete from ItemLista i
                where i.listaCompra.id = :listaId
                  and i.id in :itensIds
            """)
    int deleteItensDaLista(
            @Param("listaId") UUID listaId,
            @Param("itensIds") List<UUID> itensIds
    );

    Optional<ItemLista> findByListaCompra_IdAndItemOferta_Id(UUID listaId, UUID uuid);
}
