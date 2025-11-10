package com.barbershop.api.domain.entities;

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
}
