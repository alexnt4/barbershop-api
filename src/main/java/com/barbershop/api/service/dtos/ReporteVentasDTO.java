package com.barbershop.api.service.dtos;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Data
public class ReporteVentasDTO {
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private Double totalVentas;
    private int totalTransacciones;
    private Map<String, Double> ventasPorMetodoPago;
    private Map<String, Double> ventasPorBarbero;
}