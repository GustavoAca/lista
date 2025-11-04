package com.glaiss.lista.domain.service.itemlista;

import com.glaiss.core.domain.model.ResponsePage;
import com.glaiss.core.domain.service.BaseServiceImpl;
import com.glaiss.core.exception.RegistroNaoEncontradoException;
import com.glaiss.core.utils.SecurityContextUtils;
import com.glaiss.lista.domain.mapper.ItemListaMapper;
import com.glaiss.lista.domain.model.ItemLista;
import com.glaiss.lista.domain.model.ListaCompra;
import com.glaiss.lista.domain.model.dto.ItemListaDto;
import com.glaiss.lista.domain.model.dto.ListaCompraDto;
import com.glaiss.lista.domain.repository.itemlista.ItemListaRepository;
import com.glaiss.lista.domain.service.listascompra.ListaCompraService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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

    @Override
    public ItemListaDto salvar(ItemListaDto itemListaDto) {
        ItemLista entity = itemListaMapper.toEntity(itemListaDto);
        ItemLista salvo = salvar(entity);
        return itemListaMapper.toDto(salvo);
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

    public void adicionarItens(List<ItemListaDto> itensLista) {
        BigDecimal total = BigDecimal.ZERO;
        for (ItemListaDto item : itensLista) {
            salvar(item);
            total = total.add(item.getPreco().multiply(BigDecimal.valueOf(item.getQuantidade())));
        }
//        listaCompraService.atualizarValorTotal(ListaCompraDto.builder()
//                .id(itensLista.get(0).getListaCompraId())
//                .valorTotal(total)
//                .usuarioId(SecurityContextUtils.getId())
//                .build());
    }
}
