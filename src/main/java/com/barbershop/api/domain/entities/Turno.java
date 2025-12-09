package com.barbershop.api.domain.entities;

import java.time.LocalDateTime;

import com.barbershop.api.domain.value_objects.TurnoEstado;

import lombok.Data;
@Data
public class Turno {
    private String id;
    private String clienteId;
    private String barberoId;
    private String servicioId;
    private LocalDateTime horaAtencion;
    private LocalDateTime horaFinalizacion;
    private TurnoEstado estado;
}
