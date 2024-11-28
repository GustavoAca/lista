package com.glaiss.lista.domain.service.itemlista;

import com.glaiss.core.domain.service.BaseServiceImpl;
import com.glaiss.lista.domain.mapper.ItemListaMapper;
import com.glaiss.lista.domain.model.ItemLista;
import com.glaiss.lista.domain.model.dto.ItemListaDto;
import com.glaiss.lista.domain.repository.itemlista.ItemListaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ItemListaServiceImpl extends BaseServiceImpl<ItemLista, UUID, ItemListaRepository> implements ItemListaService {

    private final ItemListaMapper itemListaMapper;

    protected ItemListaServiceImpl(ItemListaRepository repo,
                                   ItemListaMapper itemListaMapper) {
        super(repo);
        this.itemListaMapper = itemListaMapper;
    }

    public Page<ItemListaDto> buscarItensListaPorListaCompraId(UUID compraId, Pageable pageable) {
        Page<ItemLista> itensListaPagina = repo.findByListaCompra(compraId, pageable);
        List<ItemListaDto> itensLista = itensListaPagina.getContent().stream()
                .map(itemListaMapper::toDto)
                .toList();
        return new PageImpl<>(itensLista, pageable, itensListaPagina.getTotalElements());
    }

    @Override
    public ItemListaDto salvar(ItemListaDto itemListaDto) {
        return itemListaMapper.toDto(salvar(itemListaMapper.toEntity(itemListaDto)));
    }

}
