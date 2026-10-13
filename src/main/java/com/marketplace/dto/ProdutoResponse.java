package com.marketplace.dto;

import com.marketplace.model.Produto;

import java.math.BigDecimal;

public record ProdutoResponse(
        Long id,
        String nome,
        BigDecimal valor,
        int quantidade,
        Long vendedorId,
        String vendedorNome
) {
    public static ProdutoResponse de(Produto p) {
        return new ProdutoResponse(p.getId(), p.getNome(), p.getValor(), p.getQuantidade(),
                p.getVendedor().getId(), p.getVendedor().getNome());
    }
}
