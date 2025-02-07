package com.glaiss.lista.controller;

import com.glaiss.core.domain.model.ResponsePage;
import com.glaiss.lista.domain.model.dto.PrecoDto;
import com.glaiss.lista.domain.service.preco.PrecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/precos")
public class PrecoController {

    private final PrecoService precoService;

    @Autowired
    public PrecoController(PrecoService precoService) {
        this.precoService = precoService;
    }

    @GetMapping("/{id}")
    @Cacheable(value = "Preco", key = "#id")
    public PrecoDto buscarPorId(@PathVariable UUID id) {
        return precoService.buscarPorIdDto(id);
    }

    @GetMapping("/itens/{itemId}")
    @Cacheable(value = "Preco", key = "#itemId")
    public ResponsePage<PrecoDto> buscarPrecoPorItemId(@PathVariable UUID itemId,
                                                       Pageable pageable) {
        return precoService.buscarPrecoPorItemId(itemId, pageable);
    }

    @DeleteMapping("/{id}")
    @CacheEvict(value = "Preco", allEntries = true)
    public Boolean deletar(@PathVariable UUID id) {
        return precoService.deletar(id);
    }

    @PatchMapping("/adicionar-preco")
    @CacheEvict(value = "Preco", allEntries = true)
    public PrecoDto adicionarPreco(@RequestBody PrecoDto precoDto) {
        return precoService.adicionarPreco(precoDto);
    }
}
