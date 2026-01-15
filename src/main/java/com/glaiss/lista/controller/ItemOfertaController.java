package com.glaiss.lista.controller;


import com.glaiss.core.domain.model.ResponsePage;
import com.glaiss.lista.domain.model.dto.ItemOfertaDTO;
import com.glaiss.lista.domain.model.dto.projection.vendedor.ItemOfertaProjection;
import com.glaiss.lista.domain.service.itemoferta.ItemOfertaService;
import jakarta.validation.Valid;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/itens-oferta")
public class ItemOfertaController {

    private final ItemOfertaService itemOfertaService;

    public ItemOfertaController(ItemOfertaService itemOfertaService) {
        this.itemOfertaService = itemOfertaService;
    }

    @GetMapping
    @Cacheable(value = "ItemOferta", key = "#pageable.pageNumber")
    public ResponsePage<ItemOfertaDTO> listar(@PageableDefault(size = 20) Pageable pageable){
        return itemOfertaService.listarPaginaDTO(pageable);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ItemOfertaDTO criar(@Valid @RequestBody ItemOfertaDTO itemOfertaDTO){
        return itemOfertaService.salvar(itemOfertaDTO);
    }

    @GetMapping("/{itemId}")
    @Cacheable(value = "ItemOferta", key = "#itemId + ':' + #pageable.pageNumber")
    public ResponsePage<ItemOfertaDTO> listarPorItem(@PageableDefault(size = 20) Pageable pageable,
                                                     @PathVariable UUID itemId){
        return itemOfertaService.listarPaginaPorItem(pageable, itemId);
    }

    @GetMapping("/vendedor/{vendedorId}")
    public ResponsePage<ItemOfertaProjection> listarPorVendedorId(@PageableDefault(size = 20) Pageable pageable,
                                                                  @PathVariable UUID vendedorId){
        return itemOfertaService.listarPaginaPorVendedor(pageable, vendedorId);
    }
}
