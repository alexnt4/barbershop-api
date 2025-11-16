package com.barbershop.api.infrastructure.persistence.mongodb.documents;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.barbershop.api.domain.value_objects.TurnoEstado;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "turnos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TurnoDocument {
    @Id
    private String id;
    private String clienteId;
    private String barberoId;
    private String servicioId;
    private LocalDateTime horaAtencion;
    private LocalDateTime horaFinalizacion;
    private TurnoEstado estado;
}
