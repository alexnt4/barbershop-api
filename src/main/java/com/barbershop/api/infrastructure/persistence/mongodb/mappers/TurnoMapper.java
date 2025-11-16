package com.barbershop.api.infrastructure.persistence.mongodb.mappers;

import com.barbershop.api.domain.entities.Turno;
import com.barbershop.api.infrastructure.persistence.mongodb.documents.TurnoDocument;

public class TurnoMapper {
    public static Turno toDomain(TurnoDocument document) {
        if (document == null) {
            return null;
        }

        Turno turno = new Turno();
        turno.setId(document.getId());
        turno.setBarberoId(document.getBarberoId());
        turno.setClienteId(document.getClienteId());
        turno.setServicioId(document.getServicioId());
        turno.setHoraAtencion(document.getHoraAtencion());
        turno.setHoraFinalizacion(document.getHoraFinalizacion());
        turno.setEstado(document.getEstado());
        return turno;
    }


    public static TurnoDocument toDocument(Turno turno) {
        if (turno == null) {
            return null;
        }

        TurnoDocument document = new TurnoDocument();
        document.setId(turno.getId());
        document.setBarberoId(turno.getBarberoId());
        document.setClienteId(turno.getClienteId());
        document.setServicioId(turno.getServicioId());
        document.setHoraAtencion(turno.getHoraAtencion());
        document.setHoraFinalizacion(turno.getHoraFinalizacion());
        document.setEstado(turno.getEstado());
        return document;
    }
}
