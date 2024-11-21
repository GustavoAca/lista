package com.glaiss.lista.controller;

import com.glaiss.lista.domain.model.dto.ItemDto;
import com.glaiss.lista.domain.service.item.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/{id}")
    public ResponseEntity<ItemDto> buscarPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(itemService.buscarPorIdDto(id));
    }

    @GetMapping("/listar")
    public ResponseEntity<Page<ItemDto>> listar(@PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(itemService.listarPaginadoDto(pageable));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deletar(@PathVariable UUID id) {
        return ResponseEntity.ok(itemService.deletar(id));
    }

    @PostMapping
    public ResponseEntity<ItemDto> criar(@RequestBody ItemDto itemDto) {
        return ResponseEntity.ok(itemService.criar(itemDto));
    }
}
