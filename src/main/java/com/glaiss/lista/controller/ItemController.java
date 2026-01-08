package com.glaiss.lista.controller;


import com.glaiss.core.domain.model.ResponsePage;
import com.glaiss.lista.controller.dto.ItemDTO;
import com.glaiss.lista.domain.service.item.ItemService;
import jakarta.validation.Valid;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/itens")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @Cacheable(value = "Item", key = "#itemId")
    @GetMapping("/{itemId}")
    public ItemDTO buscarPorId(@PathVariable UUID itemId) {
        return itemService.buscarPorIdDto(itemId);
    }

    @Cacheable(value = "Item", key = "#pageable.pageNumber")
    @GetMapping
    public ResponsePage<ItemDTO> listar(@PageableDefault(size = 20) Pageable pageable) {
        return itemService.listarPaginaDTO(pageable);
    }

    @DeleteMapping
    @CacheEvict(value = "Item", allEntries = true)
    public void deletar(@RequestBody List<UUID> id) {
        itemService.deletar(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ItemDTO criar(@Valid @RequestBody ItemDTO itemDTO) {
        return itemService.salvar(itemDTO);
    }

    @GetMapping("/buscar-por-nome")
    @Cacheable(value = "Item", key = "#nome + ':' + #pageable.pageNumber")
    public ResponsePage<ItemDTO> buscarPorNome(@PageableDefault Pageable pageable,
                                               @RequestParam String nome) {
        return itemService.buscarPorNomeDto(pageable, nome);
    }
}
