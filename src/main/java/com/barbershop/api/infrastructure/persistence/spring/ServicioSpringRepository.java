package com.barbershop.api.infrastructure.persistence.spring;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.barbershop.api.infrastructure.persistence.mongodb.documents.ServicioDocument;

@Repository
public interface ServicioSpringRepository extends MongoRepository<ServicioDocument, String> {
}
