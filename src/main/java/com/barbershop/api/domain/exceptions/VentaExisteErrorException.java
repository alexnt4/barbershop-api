package com.barbershop.api.domain.exceptions;

/**
 * Excepción que se lanza cuando la venta ya existe.
 */
public class VentaExisteErrorException extends RuntimeException {
    /**
     * Constructor de la excepción.
     * @param message mensaje de error
     */
    public VentaExisteErrorException(final String message) {
        super(message);
    }
}
