package com.barbershop.api.service.exceptions;

/**
 * Excepción lanzada cuando los datos de un servicio son incorrectos.
 */
public class ServicioInvalidoException extends RuntimeException {

    public ServicioInvalidoException(final String message) {
        super(message);
    }

    public ServicioInvalidoException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
