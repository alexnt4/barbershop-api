package com.barbershop.api.domain.repositories;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


import com.barbershop.api.domain.entities.Turno;

public interface TurnoRepository {

    List<Turno> findAll();
    Optional<Turno> findById(String id);
    Turno save(Turno turno);
    List<Turno> findByBarberoIdAndHoraAtencionBetween( String barberoId, LocalDateTime inicio, LocalDateTime fin);
    List<Turno> findByBarberoIdAndHoraAtencionLessThanAndHoraFinalizacionGreaterThan( String barberoId, LocalDateTime horaFinalizacionNueva, LocalDateTime horaAtencionNueva);
    List<Turno> findByHoraAtencionBetween(LocalDateTime inicio, LocalDateTime fin);
    List<Turno> findByBarberoId(String barberoId);
} 
