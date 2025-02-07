package com.glaiss.lista.controller;

import com.glaiss.core.domain.model.ResponsePage;
import com.glaiss.lista.domain.model.dto.ItemListaDto;
import com.glaiss.lista.domain.service.itemlista.ItemListaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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

    @Cacheable(value = "ItemLista", key = "#id")
    @GetMapping("/{id}")
    public ItemListaDto buscarPorId(@PathVariable UUID id) {
        return itemListaService.buscarPorIdDto(id);
    }

    @Cacheable(value = "ItemLista", key = "#pageable.pageNumber")
    @GetMapping("/listar")
    public ResponsePage<ItemListaDto> listar(@PageableDefault(size = 20) Pageable pageable) {
        return itemListaService.listarPaginadoDto(pageable);
    }

    @DeleteMapping("/{id}")
    @CacheEvict(value = "ItemLista", allEntries = true)
    public Boolean deletar(@PathVariable UUID id) {
        return itemListaService.deletar(id);
    }

    @Cacheable(value = "ItemLista", key = "#pageable.pageNumber + '_' + #listaCompraId")
    @GetMapping("/{listaCompraId}/itens-lista")
    public ResponsePage<ItemListaDto> listarItens(@PathVariable UUID listaCompraId,
                                                  @PageableDefault(size = 20) Pageable pageable) {
        return itemListaService.buscarItensListaPorListaCompraId(listaCompraId, pageable);
    }

    @PostMapping("/adicionar-itens-a-lista")
    @CacheEvict(value = "ItemLista", allEntries = true)
    public void adicionarItemLista(@RequestBody List<ItemListaDto> itensLista) {
        itemListaService.adicionarItens(itensLista);
    }
}
