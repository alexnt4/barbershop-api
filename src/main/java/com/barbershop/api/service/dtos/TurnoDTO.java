package com.barbershop.api.service.dtos;

import java.time.LocalDateTime;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TurnoDTO {
    @Schema(description = "ID del turno asignado", example = "turno123", accessMode = Schema.AccessMode.READ_ONLY)
    private String id;
    @NotNull
    @Schema(description = "ID del cliente que solicita el turno", example = "cliente123")
    private String clienteId;
    @NotNull
    @Schema(description = "ID del barbero asignado al turno", example = "barbero456")
    private String barberoId;
    @NotNull
    @Schema(description = "ID del servicio solicitado", example = "servicio789")
    private String servicioId;
    @Schema(description = "Estado actual del turno", example = "PENDIENTE", accessMode = Schema.AccessMode.READ_ONLY)
    private String estado;
    @NotNull
    @Schema(description = "Hora de atención solicitada para el turno en formato ISO 8601", example = "2024-07-01T10:30:00")
    private LocalDateTime horaAtencion;

    @Schema(description = "Hora de finalización del turno en formato ISO 8601", example = "2024-07-01T11:00:00", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime horaFinalizacion;


    static public TurnoDTO fromDomain(com.barbershop.api.domain.entities.Turno turno) {
        return new TurnoDTO(
            turno.getId(),
            turno.getClienteId(),
            turno.getBarberoId(),
            turno.getServicioId(),
            turno.getEstado().name(),
            turno.getHoraAtencion(),
            turno.getHoraFinalizacion()
        );
    }
}
