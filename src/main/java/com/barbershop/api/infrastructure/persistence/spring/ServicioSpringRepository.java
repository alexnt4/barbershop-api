package com.barbershop.api.infrastructure.persistence.spring;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.barbershop.api.infrastructure.persistence.mongodb.documents.ServicioDocument;

public interface ServicioSpringRepository extends MongoRepository<ServicioDocument, String> {
}
