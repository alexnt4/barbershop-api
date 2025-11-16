package com.barbershop.api.domain.value_objects;

import lombok.Getter;

@Getter
public enum MetodoPago {
    EFECTIVO("Efectivo"),
    TARJETA_CREDITO("Tarjeta de Crédito"),
    TARJETA_DEBITO("Tarjeta de Débito"),
    TRANSFERENCIA("Transferencia Bancaria"),
    BILLETERA_DIGITAL("Billetera Digital"),
    PUNTOS("Puntos de Fidelidad");

    private final String descripcion;

    MetodoPago(String descripcion) {
        this.descripcion = descripcion;
    }

    public static MetodoPago fromString(String metodo) {
        if (metodo == null) {
            throw new IllegalArgumentException("Método de pago no puede ser nulo");
        }
        
        for (MetodoPago mp : MetodoPago.values()) {
            if (mp.name().equalsIgnoreCase(metodo) || mp.descripcion.equalsIgnoreCase(metodo)) {
                return mp;
            }
        }
        
        throw new IllegalArgumentException("Método de pago no válido: " + metodo);
    }

    public boolean isDigital() {
        return this == TARJETA_CREDITO || this == TARJETA_DEBITO || 
               this == TRANSFERENCIA || this == BILLETERA_DIGITAL;
    }
}