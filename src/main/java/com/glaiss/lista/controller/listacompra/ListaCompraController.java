package com.glaiss.lista.controller.listacompra;

import com.glaiss.core.domain.model.ResponsePage;
import com.glaiss.lista.controller.listacompra.dto.*;
import com.glaiss.lista.domain.model.dto.projection.listacompra.ItemListaProjection;
import com.glaiss.lista.domain.service.listacompra.ListaCompraService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/listas-compras")
public class ListaCompraController {

    private final ListaCompraService listaCompraService;

    public ListaCompraController(ListaCompraService listaCompraService) {
        this.listaCompraService = listaCompraService;
    }

    @GetMapping("/{listaId}/itens")
    public ResponsePage<ItemListaProjection> listarItensPorListaCompraIdPaginaDTO(@PageableDefault(size = 20) Pageable pageable,
                                                                                  @PathVariable UUID listaId) {
        return listaCompraService.listarItensPorListaCompraIdPaginaDTO(pageable, listaId);
    }

    @GetMapping("/{listaId}")
    public ListaCompraRequest listarPorId(@PathVariable UUID listaId) {
        return listaCompraService.buscarPorIdDto(listaId);
    }

    @GetMapping
    public ResponsePage<ListaCompraRequest> listar(@PageableDefault(size = 20) Pageable pageable) {
        return listaCompraService.listar(pageable);
    }

    @PostMapping("/{listaId}/adicionar-itens")
    public List<ItemListaRequest> adicionarNaLista(@Valid @PathVariable UUID listaId,
                                                   @Valid @RequestBody List<ItemAdicionadoRequest> itemDTO) {
        return listaCompraService.adicionarItemLista(listaId, itemDTO);
    }

    @PutMapping("/{listaId}/alterar-itens")
    public Boolean alterarItens(@Valid @PathVariable UUID listaId,
                                  @Valid @RequestBody List<ItemAlteradoRequest> itensAlterados) {
        return listaCompraService.alterarItens(listaId, itensAlterados);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void criarLista(@Valid @RequestBody ListaCompraRequest listaCompraRequest) {
        listaCompraService.criarLista(listaCompraRequest);
    }

    @PutMapping("/concluir")
    public void concluirLista(@Valid @RequestBody ConcluirListaRequestDTO concluirListaRequestDTO) {
        listaCompraService.concluirLista(concluirListaRequestDTO);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable UUID id){
        listaCompraService.deletar(id);
    }

    @PutMapping
    public ListaCompraRequest atualizar(@RequestBody ListaCompraEdicaoRequest listaCompraEdicaoRequest){
       return listaCompraService.atualizar(listaCompraEdicaoRequest);
    }
}
