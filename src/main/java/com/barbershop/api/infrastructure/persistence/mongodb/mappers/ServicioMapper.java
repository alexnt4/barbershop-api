package com.barbershop.api.infrastructure.persistence.mongodb.mappers;

import org.springframework.stereotype.Component;

import com.barbershop.api.domain.entities.Servicio;
import com.barbershop.api.infrastructure.persistence.mongodb.documents.ServicioDocument;

@Component
public class ServicioMapper {

    /**
     * Convierte una entidad Servicio de dominio a un ServicioDocument para MongoDB.
     *
     * @param servicio entidad de dominio
     * @return documento de MongoDB
     */
    public ServicioDocument toDocument(final Servicio servicio) {
        if (servicio == null) {
            return null;
        }

        return new ServicioDocument(
                servicio.getId(),
                servicio.getNombre(),
                servicio.getDescripcion(),
                servicio.getDuracionMinutos(),
                servicio.getPrecio(),
                servicio.getBarberosIds()
        );
    }

    /**
     * Convierte un ServicioDocument de MongoDB a una entidad Servicio de dominio.
     *
     * @param document documento de MongoDB
     * @return entidad de dominio
     */
    public Servicio toDomain(final ServicioDocument document) {
        if (document == null) {
            return null;
        }

        return new Servicio(
                document.getId(),
                document.getNombre(),
                document.getDescripcion(),
                document.getDuracionMinutos(),
                document.getPrecio(),
                document.getBarberosIds()
        );
    }
}
