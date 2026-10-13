package com.marketplace.dto;

import com.marketplace.model.Pedido;
import com.marketplace.model.StatusPedido;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PedidoResponse(
        Long id,
        Long compradorId,
        String compradorNome,
        Long vendedorId,
        String vendedorNome,
        Long produtoId,
        String produtoNome,
        int quantidade,
        BigDecimal valorUnitario,
        BigDecimal valorBruto,
        BigDecimal desconto,
        BigDecimal valorFinal,
        long pontosUtilizados,
        long pontosGanhos,
        StatusPedido status,
        LocalDateTime criadoEm
) {
    public static PedidoResponse de(Pedido p) {
        return new PedidoResponse(
                p.getId(),
                p.getComprador().getId(), p.getComprador().getNome(),
                p.getVendedor().getId(), p.getVendedor().getNome(),
                p.getProduto().getId(), p.getProduto().getNome(),
                p.getQuantidade(), p.getValorUnitario(), p.getValorBruto(),
                p.getDesconto(), p.getValorFinal(),
                p.getPontosUtilizados(), p.getPontosGanhos(),
                p.getStatus(), p.getCriadoEm());
    }
}
