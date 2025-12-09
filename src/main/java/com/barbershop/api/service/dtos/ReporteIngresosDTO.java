package com.barbershop.api.service.dtos;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ReporteIngresosDTO {
    private LocalDateTime fechaInicio;  // Fecha de inicio del rango
    private LocalDateTime fechaFin;     // Fecha de fin del rango
    private double totalIngresos;       // Total de ingresos en el rango (puede ser BigDecimal si prefieres)
}
