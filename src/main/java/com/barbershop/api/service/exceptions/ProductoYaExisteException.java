package com.barbershop.api.service.exceptions;

/**
 * Excepción lanzada cuando ya existe un producto con el mismo nombre.
 */
public class ProductoYaExisteException extends RuntimeException {
    /**
     * Constructor con mensaje personalizado.
     *
     * @param mensaje Mensaje de error.
     */
    public ProductoYaExisteException(final String mensaje) {
        super(mensaje);
    }
}
