package com.marketplace.dto;

import com.marketplace.model.Usuario;

/** Repare: não existe campo de senha aqui. O DTO decide o que sai pela API. */
public record UsuarioResponse(
        Long id,
        String nome,
        String cpf,
        String endereco,
        String email,
        long pontos
) {
    public static UsuarioResponse de(Usuario u) {
        return new UsuarioResponse(u.getId(), u.getNome(), u.getCpf(),
                u.getEndereco(), u.getEmail(), u.getPontos());
    }
}
