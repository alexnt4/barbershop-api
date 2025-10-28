package com.barbershop.api.infrastructure.persistence.spring;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.barbershop.api.infrastructure.persistence.mongodb.documents.UsuarioDocument;

/**
 * Adaptador de Spring Data MongoDB para Usuario.
 * Proporciona métodos CRUD automáticos y finder methods personalizados.
 */
@Repository
public interface UsuarioSpringRepository extends MongoRepository<UsuarioDocument, String> {

    /**
     * Busca un usuario por su email.
     *
     * @param email correo electrónico
     * @return documento si existe
     */
    UsuarioDocument findByEmail(String email);
}
