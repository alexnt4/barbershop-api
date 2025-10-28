package com.barbershop.api.infrastructure.persistence.mongodb.mappers;

import org.springframework.stereotype.Component;

import com.barbershop.api.domain.entities.especializaciones.Cliente;
import com.barbershop.api.infrastructure.persistence.mongodb.documents.ClienteDocument;

/**
 * Mapper para convertir entre Cliente (dominio) y ClienteDocument (MongoDB).
 */
@Component
public class ClienteMapper {

    /**
     * Convierte una entidad Cliente de dominio a un ClienteDocument para MongoDB.
     *
     * @param cliente entidad de dominio
     * @return documento de MongoDB
     */
    public ClienteDocument toDocument(final Cliente cliente) {
        if (cliente == null) {
            return null;
        }

        return new ClienteDocument(
                cliente.getDni(),
                cliente.getNombre(),
                cliente.getEmail().getValue(),
                cliente.getPassword().getValue(),
                cliente.getRole().name(),
                cliente.getEdad(),
                cliente.getDireccion(),
                cliente.getGenero(),
                cliente.getTelefono()
        );
    }

    /**
     * Convierte un ClienteDocument de MongoDB a una entidad Cliente de dominio.
     *
     * @param document documento de MongoDB
     * @return entidad de dominio
     */
    public Cliente toDomain(final ClienteDocument document) {
        if (document == null) {
            return null;
        }

        return new Cliente(
                document.getDni(),
                document.getNombre(),
                document.getEmail(),
                document.getPassword(),
                document.getEdad(),
                document.getDireccion(),
                document.getGenero(),
                document.getTelefono()
        );
    }
}
