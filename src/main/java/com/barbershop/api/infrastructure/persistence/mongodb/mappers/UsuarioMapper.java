package com.barbershop.api.infrastructure.persistence.mongodb.mappers;

import org.springframework.stereotype.Component;

import com.barbershop.api.domain.entities.Usuario;
import com.barbershop.api.infrastructure.persistence.mongodb.documents.UsuarioDocument;

/**
 * Mapper para convertir entre Usuario (dominio) y UsuarioDocument (MongoDB).
 */
@Component
public class UsuarioMapper {

    /**
     * Convierte una entidad Usuario de dominio a un UsuarioDocument para MongoDB.
     *
     * @param usuario entidad de dominio
     * @return documento de MongoDB
     */
    public UsuarioDocument toDocument(final Usuario usuario) {
        if (usuario == null) {
            return null;
        }

        return new UsuarioDocument(
                usuario.getDni(),
                usuario.getNombre(),
                usuario.getEmail().getValue(),
                usuario.getPassword().getValue(),
                usuario.getRole().name()
        );
    }

    /**
     * Convierte un UsuarioDocument de MongoDB a una entidad Usuario de dominio.
     *
     * @param document documento de MongoDB
     * @return entidad de dominio
     */
    public Usuario toDomain(final UsuarioDocument document) {
        if (document == null) {
            return null;
        }

        return new Usuario(
                document.getDni(),
                document.getNombre(),
                document.getEmail(),
                document.getPassword(),
                document.getRole()
        );
    }
}
