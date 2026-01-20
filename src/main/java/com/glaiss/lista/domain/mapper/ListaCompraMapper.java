package com.glaiss.lista.domain.mapper;

import com.glaiss.core.utils.SecurityContextUtils;
import com.glaiss.lista.controller.listacompra.dto.ConcluirListaRequestDTO;
import com.glaiss.lista.controller.listacompra.dto.ItemListaConcluirRequest;
import com.glaiss.lista.controller.listacompra.dto.ItemListaRequest;
import com.glaiss.lista.controller.listacompra.dto.ListaCompraRequest;
import com.glaiss.lista.domain.model.ItemLista;
import com.glaiss.lista.domain.model.ListaCompra;
import com.glaiss.lista.domain.model.StatusLista;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Component
public class ListaCompraMapper {

    private final ItemListaMapper itemListaMapper;

    public ListaCompraMapper(ItemListaMapper itemListaMapper) {
        this.itemListaMapper = itemListaMapper;
    }

    public ListaCompra toEntity(ListaCompraRequest listaCompraRequest) {
        return ListaCompra.builder()
                .id(listaCompraRequest.id())
                .usuarioId(listaCompraRequest.usuarioId())
                .nome(listaCompraRequest.nome())
                .valorTotal(listaCompraRequest.valorTotal())
                .totalItens(listaCompraRequest.totalItens())
                .itensLista(toItemListaEntity(listaCompraRequest.itensLista()))
                .version(listaCompraRequest.version())
                .statusLista(StatusLista.builder().codigo(listaCompraRequest.statusLista()).build())
                .build();
    }

    private List<ItemLista> toItemListaEntity(List<ItemListaRequest> itensListaDTO) {
        if (Objects.isNull(itensListaDTO) || itensListaDTO.isEmpty()) {
            return null;
        }
        return itensListaDTO.stream().map(itemListaMapper::toEntity).toList();
    }

    public ListaCompraRequest toDto(ListaCompra entity) {
        return new ListaCompraRequest(entity.getId(),
                entity.getUsuarioId(),
                entity.getNome(),
                entity.getValorTotal(),
                entity.getTotalItens(),
                (toItemListaDto(entity.getItensLista())),
                entity.getVersion(),
                entity.getStatusLista().getCodigo());
    }

    private List<ItemListaRequest> toItemListaDto(List<ItemLista> itemListas) {
        if (Objects.isNull(itemListas) || itemListas.isEmpty()) {
            return null;
        }
        return itemListas.stream().map(itemListaMapper::toDto).toList();
    }

    public ListaCompra toConcluirListaCompraToEntity(ConcluirListaRequestDTO concluirListaRequestDTO){
        return ListaCompra.builder()
                .id(concluirListaRequestDTO.id())
                .usuarioId(SecurityContextUtils.getId())
                .nome(concluirListaRequestDTO.nome())
                .valorTotal(Objects.isNull(concluirListaRequestDTO.valorTotal()) ? BigDecimal.ZERO : concluirListaRequestDTO.valorTotal())
                .totalItens(Objects.isNull(concluirListaRequestDTO.totalItens()) ? (short) concluirListaRequestDTO.itensLista().size() : concluirListaRequestDTO.totalItens())
                .itensLista(itemListaConcluirRequestToItemListaEntity(concluirListaRequestDTO.itensLista()))
                .version(concluirListaRequestDTO.version())
                .build();
    }

    private List<ItemLista> itemListaConcluirRequestToItemListaEntity(List<ItemListaConcluirRequest> itemListaConcluirRequests) {
        if (Objects.isNull(itemListaConcluirRequests) || itemListaConcluirRequests.isEmpty()) {
            return null;
        }
        return itemListaConcluirRequests.stream().map(itemListaMapper::itemListaConcluirRequestToItemListaEntity).toList();
    }
}
