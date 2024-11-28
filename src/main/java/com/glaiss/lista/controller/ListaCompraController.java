package com.glaiss.lista.controller;

import com.glaiss.lista.domain.model.dto.ItemListaDto;
import com.glaiss.lista.domain.model.dto.ListaCompraDto;
import com.glaiss.lista.domain.service.listascompra.ListaCompraComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/lista-compras")
public class ListaCompraController {

    private final ListaCompraComponent listaCompraComponent;

    @Autowired
    public ListaCompraController(ListaCompraComponent listaCompraComponent) {
        this.listaCompraComponent = listaCompraComponent;
    }

    @GetMapping
    public ResponseEntity<ListaCompraDto> listar() {
        return ResponseEntity.ok(listaCompraComponent.buscarListaDeCompraPorUsuario());
    }

    @GetMapping("/{id}/itens-lista")
    public ResponseEntity<Page<ItemListaDto>> listarItens(@PathVariable UUID id,
                                                          @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(listaCompraComponent.buscarItensListaPorCompraId(id, pageable));
    }

    @DeleteMapping("/{listaCompraId}/itens-lista/{id}")
    public ResponseEntity<Boolean> apagarItenLista(@PathVariable UUID listaCompraId,
                                                   @PathVariable UUID id) {
        return ResponseEntity.ok(listaCompraComponent.removerItemLista(id));
    }

    @PostMapping("/itens-lista/local/{localId}")
    public ResponseEntity<Boolean> adicionarItemLista(@PathVariable UUID localId,
                                                      @RequestBody List<ItemListaDto> itensLista) {
        return ResponseEntity.ok(listaCompraComponent.adicionarItens(localId, itensLista));
    }

    @PostMapping
    public ResponseEntity<ListaCompraDto> criarLista() {
        return ResponseEntity.ok(listaCompraComponent.criarLista());
    }
}
