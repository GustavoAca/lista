package com.glaiss.lista.controller.vendedor;

import com.glaiss.core.domain.model.ResponsePage;
import com.glaiss.lista.controller.vendedor.dto.NovoEnderecoVendedorDTO;
import com.glaiss.lista.controller.vendedor.dto.VendedorDTO;
import com.glaiss.lista.domain.service.vendedor.VendedorService;
import jakarta.validation.Valid;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/vendedores")
public class VendedorController {

    private final VendedorService vendedorService;

    public VendedorController(VendedorService vendedorService) {
        this.vendedorService = vendedorService;
    }

    @GetMapping
    @Cacheable(value = "Vendedor", key = "#pageable.pageNumber")
    public ResponsePage<VendedorDTO> paginar(@PageableDefault(size = 20) Pageable pageable) {
        return vendedorService.listarPaginaDTO(pageable);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VendedorDTO criar(@RequestBody @Valid VendedorDTO vendedorDTO) {
        return vendedorService.salvar(vendedorDTO);
    }

    @PostMapping("adicionar-endereco")
    public Boolean adicionarEndereco(@RequestBody @Valid NovoEnderecoVendedorDTO novoEnderecoVendedorDTO) {
        return vendedorService.adicionarEndereco(novoEnderecoVendedorDTO);
    }

    @GetMapping("/buscar-por-nome")
    @Cacheable(value = "Vendedor", key = "#nome + ':' + #pageable.pageNumber")
    public ResponsePage<VendedorDTO> buscarPorNome(@PageableDefault Pageable pageable,
                                                   @RequestParam String nome) {
        return vendedorService.buscarPorNomeDto(pageable, nome);
    }

    @GetMapping("/{vendedorId}")
    @Cacheable(value = "Vendedor", key = "#vendedorId")
    public VendedorDTO buscarPorId(@PathVariable("vendedorId") UUID id) {
        return vendedorService.buscarPorIdDto(id);
    }
}
