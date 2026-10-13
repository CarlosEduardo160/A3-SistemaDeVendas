package com.marketplace.controller;

import com.marketplace.dto.LoginRequest;
import com.marketplace.dto.UsuarioRequest;
import com.marketplace.dto.UsuarioResponse;
import com.marketplace.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/usuarios")
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioResponse cadastrar(@Valid @RequestBody UsuarioRequest req) {
        return usuarioService.cadastrar(req);
    }

    @GetMapping("/usuarios/{id}")
    public UsuarioResponse buscar(@PathVariable Long id) {
        return usuarioService.buscarPorId(id);
    }

    @PostMapping("/auth/login")
    public UsuarioResponse login(@Valid @RequestBody LoginRequest req) {
        return usuarioService.login(req);
    }
}
