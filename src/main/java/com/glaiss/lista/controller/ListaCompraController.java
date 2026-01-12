package com.glaiss.lista.controller;

import com.glaiss.core.domain.model.ResponsePage;
import com.glaiss.lista.controller.dto.ItemAdicionadoDTO;
import com.glaiss.lista.controller.dto.ItemListaDTO;
import com.glaiss.lista.controller.dto.ListaCompraDTO;
import com.glaiss.lista.domain.service.listacompra.ListaCompraService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/listas-compras")
public class ListaCompraController {

    private final ListaCompraService listaCompraService;

    public ListaCompraController(ListaCompraService listaCompraService) {
        this.listaCompraService = listaCompraService;
    }

    @GetMapping("/{listaId}")
    public ResponsePage<ItemListaDTO> listarItensPorListaCompraIdPaginaDTO(@PageableDefault(size = 20) Pageable pageable,
                                                                           @PathVariable UUID listaId) {
        return listaCompraService.listarItensPorListaCompraIdPaginaDTO(pageable, listaId);
    }

    @GetMapping
    public ResponsePage<ListaCompraDTO> listar(@PageableDefault(size = 20) Pageable pageable) {
        return listaCompraService.listar(pageable);
    }

    @PostMapping("/{listaId}/adicionar-itens")
    public List<ItemListaDTO> adicionarNaLista(@Valid @PathVariable UUID listaId,
                                               @Valid @RequestBody List<ItemAdicionadoDTO> itemDTO) {
        return listaCompraService.adicionarItemLista(listaId, itemDTO);
    }

    @DeleteMapping("/{listaId}/remover-itens")
    public Boolean removerDaLista(@Valid @PathVariable UUID listaId,
                                  @Valid @RequestBody List<UUID> itensLista) {
        return listaCompraService.removerDaLista(listaId, itensLista);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void criarLista(@Valid @RequestBody ListaCompraDTO listaCompraDTO) {
        listaCompraService.criarLista(listaCompraDTO);
    }
}
