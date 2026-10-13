package com.marketplace.exception;

/** Violação de regra de negócio (ex.: estoque insuficiente, comprar o próprio produto). */
public class NegocioException extends RuntimeException {
    public NegocioException(String mensagem) {
        super(mensagem);
    }
}
