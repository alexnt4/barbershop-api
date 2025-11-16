package com.barbershop.api.domain.value_objects;

public enum TurnoEstado {
    PENDIENTE,
    CONFIRMADO,
    CANCELADO,
    COMPLETADO;

     public static TurnoEstado fromString(String estado) {
        for (TurnoEstado te : TurnoEstado.values()) {
            if (te.name().equalsIgnoreCase(estado)) {
                return te;
            }
        }
        throw new IllegalArgumentException("Estado de turno inválido: " + estado);
     }
}
