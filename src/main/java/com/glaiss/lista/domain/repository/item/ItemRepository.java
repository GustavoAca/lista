package com.glaiss.lista.domain.repository.item;

import com.glaiss.core.domain.repository.BaseRepository;
import com.glaiss.lista.domain.model.Item;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ItemRepository extends BaseRepository<Item, UUID> {
}
