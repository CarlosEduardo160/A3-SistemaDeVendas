package com.marketplace.controller;

import com.marketplace.dto.PedidoRequest;
import com.marketplace.dto.PedidoResponse;
import com.marketplace.dto.StatusRequest;
import com.marketplace.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoResponse criar(@Valid @RequestBody PedidoRequest req) {
        return pedidoService.criar(req);
    }

    @GetMapping
    public List<PedidoResponse> listar(
            @RequestParam(required = false) Long compradorId,
            @RequestParam(required = false) Long vendedorId) {
        return pedidoService.listar(compradorId, vendedorId);
    }

    @GetMapping("/{id}")
    public PedidoResponse buscar(@PathVariable Long id) {
        return pedidoService.buscarPorId(id);
    }

    @PatchMapping("/{id}/status")
    public PedidoResponse atualizarStatus(@PathVariable Long id, @Valid @RequestBody StatusRequest req) {
        return pedidoService.atualizarStatus(id, req.status());
    }
}
