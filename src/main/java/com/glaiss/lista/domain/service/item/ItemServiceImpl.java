package com.glaiss.lista.domain.service.item;

import com.glaiss.core.domain.service.BaseServiceImpl;
import com.glaiss.core.exception.RegistroNaoEncontradoException;
import com.glaiss.lista.domain.mapper.ItemMapper;
import com.glaiss.lista.domain.model.Item;
import com.glaiss.lista.domain.model.dto.ItemDto;
import com.glaiss.lista.domain.repository.item.ItemRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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

    @Override
    public ItemDto buscarPorIdDto(UUID id) {
        return itemMapper.toDto(buscarPorId(id)
                .orElseThrow(() -> new RegistroNaoEncontradoException(id, Item.class.getName())));
    }

    @Override
    public Page<ItemDto> listarPaginadoDto(Pageable pageable) {
        Page<Item> itensPaginado = listarPagina(pageable);
        List<ItemDto> itens = itensPaginado.getContent().stream()
                .map(itemMapper::toDto).toList();
        return new PageImpl<>(itens, pageable, itensPaginado.getTotalElements());
    }

    @Override
    public ItemDto criar(ItemDto itemDto) {
        return itemMapper.toDto(salvar(itemMapper.toEntity(itemDto)));
    }

    @Override
    public ItemDto adicionarNovoPreco(ItemDto itemDto) {
        Item item = buscarPorId(itemDto.getId())
                .orElseThrow(() -> new RegistroNaoEncontradoException(itemDto.getId(), Item.class.getName()));
        item.getPrecos().addAll(itemMapper.toEntity(itemDto).getPrecos());

        return itemMapper.toDto(salvar(item));
    }
}
