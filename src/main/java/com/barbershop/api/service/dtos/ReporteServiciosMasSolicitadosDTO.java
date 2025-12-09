package com.barbershop.api.service.dtos;

import lombok.Data;
import java.util.Map;
import java.time.LocalDateTime;

@Data
public class ReporteServiciosMasSolicitadosDTO {

    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private Map<String, Integer> serviciosSolicitados;
}
