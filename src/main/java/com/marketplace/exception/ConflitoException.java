package com.marketplace.exception;

/** Dado que já existe (ex.: CPF ou e-mail repetido). */
public class ConflitoException extends RuntimeException {
    public ConflitoException(String mensagem) {
        super(mensagem);
    }
}
