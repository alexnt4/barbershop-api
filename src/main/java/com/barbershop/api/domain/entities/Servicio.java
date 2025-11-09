package com.barbershop.api.domain.entities;

import lombok.Getter;
import lombok.Setter;

/**
 * Clase temporal para Servicio (mientras tu compañero la desarrolla)
 */
@Getter
@Setter
public class Servicio {
    private String id;
    private String nombre;
    private String descripcion;
    private double precioBase;
    private int duracionEstimada; // en minutos
    
    public Servicio(String id, String nombre, String descripcion, double precioBase, int duracionEstimada) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precioBase = precioBase;
        this.duracionEstimada = duracionEstimada;
    }
    
    // Constructor básico para el mapper
    public Servicio(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = "";
        this.precioBase = 0.0;
        this.duracionEstimada = 0;
    }
}
