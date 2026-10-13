package com.marketplace.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

public record UsuarioRequest(
        @NotBlank(message = "Nome é obrigatório") String nome,
        @NotBlank(message = "CPF é obrigatório") @CPF(message = "CPF inválido") String cpf,
        @NotBlank(message = "Endereço é obrigatório") String endereco,
        @NotBlank(message = "E-mail é obrigatório") @Email(message = "E-mail inválido") String email,
        @NotBlank(message = "Senha é obrigatória")
        @Size(min = 6, message = "A senha deve ter pelo menos 6 caracteres") String senha
) {
}
