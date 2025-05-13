package com.glaiss.lista.domain.service.item;

import com.glaiss.core.domain.model.ResponsePage;
import com.glaiss.core.domain.service.BaseServiceImpl;
import com.glaiss.core.exception.RegistroNaoEncontradoException;
import com.glaiss.lista.domain.mapper.ItemMapper;
import com.glaiss.lista.domain.model.Item;
import com.glaiss.lista.domain.model.dto.ItemDto;
import com.glaiss.lista.domain.repository.item.ItemRepository;
import com.glaiss.lista.domain.service.preco.PrecoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ItemServiceImpl extends BaseServiceImpl<Item, UUID, ItemRepository> implements ItemService {

    private final ItemMapper itemMapper;
    private final PrecoService precoService;

    protected ItemServiceImpl(ItemRepository repo,
                              ItemMapper itemMapper, PrecoService precoService) {
        super(repo);
        this.itemMapper = itemMapper;
        this.precoService = precoService;
    }

    @Override
    public ItemDto buscarPorIdDto(UUID id) {
        return itemMapper.toDto(buscarPorId(id)
                .orElseThrow(() -> new RegistroNaoEncontradoException(id, Item.class.getName())));
    }

    @Override
    public ResponsePage<ItemDto> listarPaginadoDto(Pageable pageable) {
        Page<Item> itensPaginado = listarPagina(pageable);
        List<ItemDto> itens = itensPaginado.getContent().stream()
                .map(itemMapper::toDto).toList();
        return new ResponsePage<>(itens, pageable.getPageNumber(), pageable.getPageSize(), itensPaginado.getTotalElements());
    }

    @Override
    public ItemDto criar(ItemDto itemDto) {
        var precos = itemDto.getPrecos();
        itemDto.setPrecos(new ArrayList<>());
        ItemDto itemSalvo = itemMapper.toDto(salvar(itemMapper.toEntity(itemDto)));
        precos.forEach(p -> {
            p.setItemId(itemSalvo.getId());
            precoService.salvar(p);
        });
        return itemSalvo;
    }

    @Override
    public void deletar(List<UUID> itensId) {
        for (UUID id : itensId) {
            deletar(id);
        }
    }
}
