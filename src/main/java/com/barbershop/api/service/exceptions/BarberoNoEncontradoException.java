package com.barbershop.api.service.exceptions;

/**
 * Excepción lanzada cuando no se encuentra un barbero.
 */
public class BarberoNoEncontradoException extends RuntimeException {

    public BarberoNoEncontradoException(final String message) {
        super(message);
    }

    public BarberoNoEncontradoException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
