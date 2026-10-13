package com.marketplace.controller;

import com.marketplace.dto.ProdutoRequest;
import com.marketplace.dto.ProdutoResponse;
import com.marketplace.dto.ProdutoUpdateRequest;
import com.marketplace.service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProdutoResponse cadastrar(@Valid @RequestBody ProdutoRequest req) {
        return produtoService.cadastrar(req);
    }

    @GetMapping
    public List<ProdutoResponse> listar(
            @RequestParam(required = false) Long vendedorId,
            @RequestParam(defaultValue = "false") boolean apenasDisponiveis) {
        return produtoService.listar(vendedorId, apenasDisponiveis);
    }

    @GetMapping("/{id}")
    public ProdutoResponse buscar(@PathVariable Long id) {
        return produtoService.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public ProdutoResponse atualizar(@PathVariable Long id, @Valid @RequestBody ProdutoUpdateRequest req) {
        return produtoService.atualizar(id, req);
    }
}
