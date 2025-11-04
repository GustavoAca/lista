package com.glaiss.lista.domain.service.listascompra;

import com.glaiss.core.domain.model.ResponsePage;
import com.glaiss.core.domain.service.BaseServiceImpl;
import com.glaiss.core.exception.RegistroNaoEncontradoException;
import com.glaiss.core.utils.SecurityContextUtils;
import com.glaiss.lista.domain.mapper.ListaCompraMapper;
import com.glaiss.lista.domain.model.ItemLista;
import com.glaiss.lista.domain.model.ListaCompra;
import com.glaiss.lista.domain.model.dto.ItemListaDto;
import com.glaiss.lista.domain.model.dto.ListaCompraDto;
import com.glaiss.lista.domain.repository.listascompra.ListaCompraRepository;
import com.glaiss.lista.domain.service.itemlista.ItemListaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ListaCompraServiceImpl extends BaseServiceImpl<ListaCompra, UUID, ListaCompraRepository> implements ListaCompraService {

    private final ListaCompraMapper listaCompraMapper;
    private final ItemListaService itemListaService;

    @Autowired
    protected ListaCompraServiceImpl(ListaCompraRepository repo,
                                     ListaCompraMapper listaCompraMapper,
                                     ItemListaService itemListaService
    ) {
        super(repo);
        this.listaCompraMapper = listaCompraMapper;
        this.itemListaService = itemListaService;
    }


    @Override
    public ListaCompraDto salvar(@Valid List<ItemListaDto> itensDto) {
        ListaCompra listaCompra = ListaCompra.builder()
                .usuarioId(SecurityContextUtils.getId())
                .valorTotal(BigDecimal.ZERO)
                .build();
        listaCompra = salvar(listaCompra);

        BigDecimal total = BigDecimal.ZERO;
        for (ItemListaDto itemDto : itensDto) {
            itemDto.setListaCompraId(listaCompra.getId());
            itemListaService.salvar(itemDto);
            total = total.add(itemDto.getPreco().multiply(BigDecimal.valueOf(itemDto.getQuantidade())));
        }

        listaCompra.setValorTotal(total);
        listaCompra = salvar(listaCompra);

        return listaCompraMapper.toDto(listaCompra);
    }

    @Override
    public ResponsePage<ListaCompraDto> listarPaginaDto(Pageable pageable) {
        Page<ListaCompra> listaCompraPage = repo.findByUsuarioId(SecurityContextUtils.getId(), pageable);
        var listaCompra = listaCompraPage.getContent().stream()
                .map(listaCompraMapper::toDto).toList();
        return new ResponsePage<>(listaCompra, pageable.getPageNumber(), pageable.getPageSize(), listaCompraPage.getTotalElements());
    }

    @Override
    public ListaCompraDto buscarPorIdDto(UUID id) {
        return listaCompraMapper.toDto(buscarPorId(id)
                .orElseThrow(() -> new RegistroNaoEncontradoException(id, "Lista de compra")));
    }

    @Override
    public ListaCompraDto atualizarValorTotal(ListaCompraDto listaCompraDto) {
        ListaCompra listaCompra = buscarPorId(listaCompraDto.getId())
                .orElseThrow(() -> new RegistroNaoEncontradoException(listaCompraDto.getId(), listaCompraDto.getClass().getName()));
        listaCompra.setValorTotal(listaCompraDto.getValorTotal());
        return listaCompraMapper.toDto(salvar(listaCompra));
    }
}
