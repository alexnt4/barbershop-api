package com.barbershop.api.domain.repositories;

import java.util.List;
import java.util.Optional;
import com.barbershop.api.domain.entities.Servicio;

public interface ServicioRepository {
    Optional<Servicio> findById(String id);
    List<Servicio> findAll();
    Servicio save(Servicio servicio);
}
