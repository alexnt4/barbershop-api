package com.barbershop.api.service.exceptions;

/**
 * Excepción lanzada cuando no se encuentra un usuario.
 */
public class UsuarioNoEncontradoException extends RuntimeException {

    public UsuarioNoEncontradoException(final String message) {
        super(message);
    }

    public UsuarioNoEncontradoException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
