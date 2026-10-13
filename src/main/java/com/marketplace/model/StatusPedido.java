package com.marketplace.model;

public enum StatusPedido {
    PENDENTE,
    PAGO,
    ENVIADO,
    ENTREGUE,
    CANCELADO;

    /** Define o "fluxo" permitido: um pedido não pode, por exemplo, voltar de ENTREGUE para PENDENTE. */
    public boolean podeIrPara(StatusPedido destino) {
        return switch (this) {
            case PENDENTE -> destino == PAGO || destino == CANCELADO;
            case PAGO -> destino == ENVIADO || destino == CANCELADO;
            case ENVIADO -> destino == ENTREGUE;
            case ENTREGUE, CANCELADO -> false;
        };
    }
}
