package com.barbershop.api.domain.entities.especializaciones;
import com.barbershop.api.domain.entities.Servicio;

import lombok.Getter;
import lombok.Setter;

/**
 * Representa un servicio vendido en una venta.
 */
@Getter
@Setter
public class DetalleServicio {
    private Servicio servicio;
    private double precio;
    private int duracion; // en minutos

    public DetalleServicio(final Servicio servicioParam, final double precioParam, final int duracionParam) {
        this.servicio = servicioParam;
        this.precio = precioParam;
        this.duracion = duracionParam;
    }
}
