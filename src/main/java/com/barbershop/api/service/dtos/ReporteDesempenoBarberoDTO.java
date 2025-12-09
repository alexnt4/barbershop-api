package com.barbershop.api.service.dtos;

import java.time.LocalDateTime;
import java.util.Map;

import lombok.Data;

/**
 * DTO para el reporte de desempeño de barberos.
 * Contiene el rango de fechas del reporte y la cantidad de servicios realizados por cada barbero.
 */
@Data
public class ReporteDesempenoBarberoDTO {

    /**
     * Fecha de inicio del rango del reporte.
     */
    private LocalDateTime fechaInicio;

    /**
     * Fecha de fin del rango del reporte.
     */
    private LocalDateTime fechaFin;

    /**
     * Mapa que contiene el nombre del barbero como clave
     * y la cantidad de servicios completados como valor.
     */
    private Map<String, Integer> serviciosPorBarbero;
}
