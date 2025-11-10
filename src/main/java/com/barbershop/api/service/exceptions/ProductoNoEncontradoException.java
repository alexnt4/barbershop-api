package com.barbershop.api.service.exceptions;

/**
 * Excepción lanzada cuando no se encuentra un producto.
 */
public class ProductoNoEncontradoException extends RuntimeException {
    /**
     * Constructor con mensaje personalizado.
     *
     * @param mensaje Mensaje de error.
     */
    public ProductoNoEncontradoException(final String mensaje) {
        super(mensaje);
    }
}
