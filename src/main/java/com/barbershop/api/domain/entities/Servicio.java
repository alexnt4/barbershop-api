package com.barbershop.api.domain.entities;

<<<<<<< HEAD
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
=======
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * La clase Servicio representa un servicio ofrecido en la barbería.
 * Contiene la información del servicio y su relación con los barberos que lo prestan.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Servicio {

    /**
     * Identificador único del servicio.
     */
    private String id;

    /**
     * Nombre del servicio.
     */
    private String nombre;

    /**
     * Descripción del servicio.
     */
    private String descripcion;

    /**
     * Duración del servicio en minutos.
     */
    private int duracionMinutos;

    /**
     * Precio del servicio.
     */
    private double precio;

    /**
     * Lista de IDs de barberos que ofrecen el servicio.
     */
    private List<String> barberosIds;
>>>>>>> feature/services
}
