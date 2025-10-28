package com.barbershop.api.infrastructure.persistence.spring;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.barbershop.api.infrastructure.persistence.mongodb.documents.BarberoDocument;

/**
 * Adaptador de Spring Data MongoDB para Barbero.
 * Proporciona métodos CRUD automáticos y finder methods personalizados.
 */
@Repository
public interface BarberoSpringRepository extends MongoRepository<BarberoDocument, String> {

    /**
     * Busca un barbero por su email.
     *
     * @param email correo electrónico
     * @return documento si existe
     */
    BarberoDocument findByEmail(String email);
}
