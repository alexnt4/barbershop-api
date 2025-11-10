package com.barbershop.api.domain.repositories;

import java.util.List;
import java.util.Optional;
<<<<<<< HEAD
import com.barbershop.api.domain.entities.Servicio;

public interface ServicioRepository {
    Optional<Servicio> findById(String id);
    List<Servicio> findAll();
    Servicio save(Servicio servicio);
=======

import com.barbershop.api.domain.entities.Servicio;

public interface ServicioRepository {
    List<Servicio> findAll();
    Optional<Servicio> findById(String id);
    Servicio save(Servicio servicio);
    void deleteById(String id);
>>>>>>> feature/services
}
