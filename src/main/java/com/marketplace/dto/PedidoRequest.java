package com.marketplace.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/** usarPontos é OPCIONAL: se vier ausente/false, o comprador não usa pontos. */
public record PedidoRequest(
        @NotNull(message = "compradorId é obrigatório") Long compradorId,
        @NotNull(message = "produtoId é obrigatório") Long produtoId,
        @NotNull(message = "Quantidade é obrigatória")
        @Positive(message = "Quantidade deve ser maior que zero") Integer quantidade,
        boolean usarPontos
) {
}
