package com.barbershop.api.domain.repositories;

import java.util.List;
import java.util.Optional;

import com.barbershop.api.domain.entities.Servicio;

public interface ServicioRepository {
    List<Servicio> findAll();
    Optional<Servicio> findById(String id);
    Servicio save(Servicio servicio);
    void deleteById(String id);
}
