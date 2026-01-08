package com.glaiss.lista.domain.repository;

import com.glaiss.core.domain.repository.BaseRepository;
import com.glaiss.lista.domain.model.ItemOferta;
import feign.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ItemOfertaRepository extends BaseRepository<ItemOferta, UUID> {
    @Query("""
               SELECT io
               FROM ItemOferta io
               WHERE io.item.id = :itemId
            """)
    Page<ItemOferta> findByItemId(
            @Param("itemId") UUID itemId,
            Pageable pageable
    );

}
