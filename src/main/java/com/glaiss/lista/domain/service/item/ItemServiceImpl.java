package com.glaiss.lista.domain.service.item;

import com.glaiss.core.domain.model.ResponsePage;
import com.glaiss.core.domain.service.BaseServiceImpl;
import com.glaiss.core.exception.RegistroNaoEncontradoException;
import com.glaiss.lista.controller.item.dto.ItemDTO;
import com.glaiss.lista.domain.mapper.ItemMapper;
import com.glaiss.lista.domain.model.Item;
import com.glaiss.lista.domain.repository.ItemRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ItemServiceImpl extends BaseServiceImpl<Item, UUID, ItemRepository> implements ItemService {

    private final ItemMapper itemMapper;

    protected ItemServiceImpl(ItemRepository repo,
                              ItemMapper itemMapper) {
        super(repo);
        this.itemMapper = itemMapper;
    }

    public ItemDTO salvar(ItemDTO item) {
        Item itemEntity= itemMapper.toEntity(item);
        itemEntity.setIsAtivo(Boolean.TRUE);
        return itemMapper.toDto(salvar(itemEntity));
    }

    public ResponsePage<ItemDTO> listarPaginaDTO(org.springframework.data.domain.Pageable pageable){
        Page<Item> itemPage = repo.findAll(pageable);
        var listaItem = itemPage.getContent().stream().map(itemMapper::toDto).toList();
        return new ResponsePage<>(listaItem, pageable.getPageNumber(), pageable.getPageSize(), itemPage.getTotalElements());
    }

    @Override
    public void deletar(List<UUID> itensId) {
        for (UUID id : itensId) {
            deletar(id);
        }
    }

    @Override
    public ItemDTO buscarPorIdDto(UUID id) {
        return itemMapper.toDto(buscarPorId(id)
                .orElseThrow(() -> new RegistroNaoEncontradoException(id, Item.class.getName())));
    }

    @Override
    public ResponsePage<ItemDTO> buscarPorNomeDto(Pageable pageable, String nome){
        Page<Item> itemPage = repo.findByNomeContainingIgnoreCase(pageable, nome);
        var listaItem = itemPage.getContent().stream().map(itemMapper::toDto).toList();
        return new ResponsePage<>(listaItem, pageable.getPageNumber(), pageable.getPageSize(), itemPage.getTotalElements());
    }
}
