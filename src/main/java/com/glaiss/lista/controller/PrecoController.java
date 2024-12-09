package com.glaiss.lista.controller;

import com.glaiss.lista.domain.model.dto.PrecoDto;
import com.glaiss.lista.domain.service.preco.PrecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<PrecoDto> buscarPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(precoService.buscarPorIdDto(id));
    }

    @CacheEvict(value = "Preco", key = "#id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deletar(@PathVariable UUID id) {
        return ResponseEntity.ok(precoService.deletar(id));
    }
}
