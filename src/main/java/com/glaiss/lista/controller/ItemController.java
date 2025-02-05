package com.glaiss.lista.controller;

import com.glaiss.core.domain.model.ResponsePage;
import com.glaiss.lista.domain.model.dto.ItemDto;
import com.glaiss.lista.domain.service.item.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/itens")
public class ItemController {

    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @Cacheable(value = "Item", key = "#id")
    @GetMapping("/{id}")
    public ItemDto buscarPorId(@PathVariable UUID id) {
        return itemService.buscarPorIdDto(id);
    }

    @Cacheable(value = "Item", key = "#pageable.pageNumber")
    @GetMapping("/listar")
    public ResponsePage<ItemDto> listar(@PageableDefault(size = 20) Pageable pageable) {
        return itemService.listarPaginadoDto(pageable);
    }

    @DeleteMapping("/{id}")
    public Boolean deletar(@PathVariable UUID id) {
        return itemService.deletar(id);
    }

    @PostMapping
    public ItemDto criar(@RequestBody ItemDto itemDto) {
        return itemService.criar(itemDto);
    }
}
