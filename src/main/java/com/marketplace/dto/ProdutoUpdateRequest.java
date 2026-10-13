package com.marketplace.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record ProdutoUpdateRequest(
        @NotBlank(message = "Nome é obrigatório") String nome,
        @NotNull(message = "Valor é obrigatório")
        @DecimalMin(value = "0.01", message = "Valor deve ser maior que zero") BigDecimal valor,
        @NotNull(message = "Quantidade é obrigatória")
        @PositiveOrZero(message = "Quantidade não pode ser negativa") Integer quantidade
) {
}
