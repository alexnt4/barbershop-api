package com.barbershop.api.service.exceptions;

public class VentaNoEncontradaException extends RuntimeException {
    public VentaNoEncontradaException(String id) {
        super("Venta no encontrada con ID: " + id);
    }
}