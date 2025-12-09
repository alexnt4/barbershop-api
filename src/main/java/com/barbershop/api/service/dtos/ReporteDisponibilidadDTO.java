package com.barbershop.api.service.dtos;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
public class ReporteDisponibilidadDTO {
    private LocalDateTime fechaConsulta; // Fecha/hora en que se genera el reporte
    private Map<String, List<IntervaloDisponible>> disponibilidadPorBarbero; // clave: nombre del barbero

    @Data
    public static class IntervaloDisponible {
        private LocalDateTime inicio;
        private LocalDateTime fin;

        public IntervaloDisponible(LocalDateTime inicio, LocalDateTime fin) {
            this.inicio = inicio;
            this.fin = fin;
        }
    }
}
