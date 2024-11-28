package com.glaiss.lista.domain.service.listascompra;

import com.glaiss.core.exception.GlaissException;
import com.glaiss.core.utils.SecurityContextUtils;
import com.glaiss.lista.domain.model.dto.ItemListaDto;
import com.glaiss.lista.domain.model.dto.ListaCompraDto;
import com.glaiss.lista.domain.service.itemlista.ItemListaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class ListaCompraComponent {

    private final ListaCompraService listaCompraService;
    private final ItemListaService itemListaService;

    @Autowired
    public ListaCompraComponent(ListaCompraService listaCompraService,
                                ItemListaService itemListaService) {
        this.listaCompraService = listaCompraService;
        this.itemListaService = itemListaService;
    }

    public ListaCompraDto buscarListaDeCompraPorUsuario() {
        return listaCompraService.buscarListaCompraPorUsuarioId();
    }

    public Page<ItemListaDto> buscarItensListaPorCompraId(UUID listaCompraId,
                                                          Pageable pageable) {
        return itemListaService.buscarItensListaPorListaCompraId(listaCompraId, pageable);
    }

    public Boolean removerItemLista(UUID itemListaId) {
        return itemListaService.deletar(itemListaId);
    }

    public Boolean adicionarItens(UUID localId, List<ItemListaDto> itensLista) {
        try {
            itensLista.forEach(i -> {
                i.getItem().adicionarLocalDoPreco(localId);
                itemListaService.salvar(i);
            });
            return Boolean.TRUE;
        } catch (RuntimeException e) {
            throw new GlaissException();
        }
    }

    public ListaCompraDto criarLista() {
        return listaCompraService.salvar(ListaCompraDto.builder()
                .usuarioId(SecurityContextUtils.getId())
                .build());
    }
}
