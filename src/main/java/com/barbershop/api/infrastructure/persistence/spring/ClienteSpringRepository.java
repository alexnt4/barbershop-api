package com.barbershop.api.infrastructure.persistence.spring;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.barbershop.api.infrastructure.persistence.mongodb.documents.ClienteDocument;

/**
 * Adaptador de Spring Data MongoDB para Cliente.
 * Proporciona métodos CRUD automáticos y finder methods personalizados.
 */
@Repository
public interface ClienteSpringRepository extends MongoRepository<ClienteDocument, String> {

    /**
     * Busca un cliente por su email.
     *
     * @param email correo electrónico
     * @return documento si existe
     */
    ClienteDocument findByEmail(String email);
}
