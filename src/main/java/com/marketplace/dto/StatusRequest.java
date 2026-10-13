package com.marketplace.dto;

import com.marketplace.model.StatusPedido;
import jakarta.validation.constraints.NotNull;

public record StatusRequest(
        @NotNull(message = "Status é obrigatório") StatusPedido status
) {
}
