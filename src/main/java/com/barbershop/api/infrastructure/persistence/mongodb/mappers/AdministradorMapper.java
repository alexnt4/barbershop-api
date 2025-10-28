package com.barbershop.api.infrastructure.persistence.mongodb.mappers;

import org.springframework.stereotype.Component;

import com.barbershop.api.domain.entities.especializaciones.Administrador;
import com.barbershop.api.infrastructure.persistence.mongodb.documents.AdministradorDocument;

/**
 * Mapper para convertir entre Administrador (dominio) y AdministradorDocument (MongoDB).
 */
@Component
public class AdministradorMapper {

    /**
     * Convierte una entidad Administrador de dominio a un AdministradorDocument para MongoDB.
     *
     * @param administrador entidad de dominio
     * @return documento de MongoDB
     */
    public AdministradorDocument toDocument(final Administrador administrador) {
        if (administrador == null) {
            return null;
        }

        return new AdministradorDocument(
                administrador.getDni(),
                administrador.getNombre(),
                administrador.getEmail().getValue(),
                administrador.getPassword().getValue(),
                administrador.getRole().name()
        );
    }

    /**
     * Convierte un AdministradorDocument de MongoDB a una entidad Administrador de dominio.
     *
     * @param document documento de MongoDB
     * @return entidad de dominio
     */
    public Administrador toDomain(final AdministradorDocument document) {
        if (document == null) {
            return null;
        }

        return new Administrador(
                document.getDni(),
                document.getNombre(),
                document.getEmail(),
                document.getPassword()
        );
    }
}
