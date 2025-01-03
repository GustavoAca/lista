package com.glaiss.lista.controller;

import com.glaiss.lista.domain.model.dto.ItemListaDto;
import com.glaiss.lista.domain.service.itemlista.ItemListaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/itens-lista")
public class ItemListaController {

    private final ItemListaService itemListaService;

    @Autowired
    public ItemListaController(ItemListaService itemListaService) {
        this.itemListaService = itemListaService;
    }

    @Cacheable(value = "ItemListaDto", key = "#id")
    @GetMapping("/{id}")
    public ResponseEntity<ItemListaDto> buscarPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(itemListaService.buscarPorIdDto(id));
    }

    @Cacheable(value = "ItemListaDto", key = "#pageable.pageNumber")
    @GetMapping("/listar")
    public ResponseEntity<Page<ItemListaDto>> listar(@PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(itemListaService.listarPaginadoDto(pageable));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deletar(@PathVariable UUID id) {
        return ResponseEntity.ok(itemListaService.deletar(id));
    }

    @Cacheable(value = "ItemListaDto", key = "#pageable.pageNumber + '_' + #listaCompraId")
    @GetMapping("/{listaCompraId}/itens-lista")
    public ResponseEntity<Page<ItemListaDto>> listarItens(@PathVariable UUID listaCompraId,
                                                          @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(itemListaService.buscarItensListaPorListaCompraId(listaCompraId, pageable));
    }

    @CacheEvict(value = "ItemListaDto", key = "#id")
    @DeleteMapping("/lista-de-compra/itens-lista/{id}")
    public ResponseEntity<Boolean> apagarItenLista(@PathVariable UUID id) {
        return ResponseEntity.ok(itemListaService.removerItemLista(id));
    }

    @PostMapping("/itens-lista/local/{localId}")
    public ResponseEntity<Boolean> adicionarItemLista(@PathVariable UUID localId,
                                                      @RequestBody List<ItemListaDto> itensLista) {
        return ResponseEntity.ok(itemListaService.adicionarItens(localId, itensLista));
    }
}
