package com.barbershop.api.domain.repositories;

import com.barbershop.api.domain.entities.Proveedor;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProveedorRepository extends MongoRepository<Proveedor, String> {
}
