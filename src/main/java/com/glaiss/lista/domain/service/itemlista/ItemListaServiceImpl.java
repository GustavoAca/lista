package com.glaiss.lista.domain.service.itemlista;

import com.glaiss.core.domain.model.ResponsePage;
import com.glaiss.core.domain.service.BaseServiceImpl;
import com.glaiss.core.exception.GlaissException;
import com.glaiss.core.exception.RegistroNaoEncontradoException;
import com.glaiss.lista.domain.mapper.ItemListaMapper;
import com.glaiss.lista.domain.model.ItemLista;
import com.glaiss.lista.domain.model.dto.ItemListaDto;
import com.glaiss.lista.domain.repository.itemlista.ItemListaRepository;
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

    public ResponsePage<ItemListaDto> buscarItensListaPorListaCompraId(UUID compraId, Pageable pageable) {
        ResponsePage<ItemLista> itensListaPagina = repo.findByListaCompraId(compraId, pageable);
        List<ItemListaDto> itensLista = itensListaPagina.getContent().stream()
                .map(itemListaMapper::toDto)
                .toList();
        return new ResponsePage<>(itensLista, pageable.getPageNumber(), pageable.getPageSize(), itensListaPagina.getTotalElements());
    }

    private ItemListaDto salvar(ItemListaDto itemListaDto) {
        return itemListaMapper.toDto(salvar(itemListaMapper.toEntity(itemListaDto)));
    }

    @Override
    public ResponsePage<ItemListaDto> listarPaginadoDto(Pageable pageable) {
        ResponsePage<ItemLista> itensPaginado = listarPagina(pageable);
        List<ItemListaDto> itens = itensPaginado.getContent().stream()
                .map(itemListaMapper::toDto).toList();
        return new ResponsePage<>(itens, pageable.getPageNumber(), pageable.getPageSize(), itensPaginado.getTotalElements());
    }

    @Override
    public ItemListaDto buscarPorIdDto(UUID id) {
        return itemListaMapper.toDto(buscarPorId(id)
                .orElseThrow(() -> new RegistroNaoEncontradoException(id, "ItemLista")));
    }

    public Boolean removerItemLista(UUID itemListaId) {
        return deletar(itemListaId);
    }

    public Boolean adicionarItens(UUID localId, List<ItemListaDto> itensLista) {
        try {
            itensLista.forEach(i -> {
                i.getItem().adicionarLocalDoPreco(localId);
                salvar(i);
            });
            return Boolean.TRUE;
        } catch (RuntimeException e) {
            throw new GlaissException();
        }
    }
}
