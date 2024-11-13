package com.glaiss.lista.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/compras")
public class CompraController {

    @GetMapping("/listar")
    public ResponseEntity<String> listar() {
        return ResponseEntity.ok("Iniciado!");
    }
}
