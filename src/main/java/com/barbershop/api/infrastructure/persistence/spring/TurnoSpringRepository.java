package com.barbershop.api.infrastructure.persistence.spring;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.barbershop.api.infrastructure.persistence.mongodb.documents.TurnoDocument;

@Repository
public interface TurnoSpringRepository extends MongoRepository<TurnoDocument, String> {
    List<TurnoDocument> findByBarberoIdAndHoraAtencionBetween( String barberoId, LocalDateTime inicio, LocalDateTime fin); 
    List<TurnoDocument> findByBarberoIdAndHoraAtencionLessThanAndHoraFinalizacionGreaterThan( String barberoId, LocalDateTime horaFinalizacionNueva, LocalDateTime horaAtencionNueva);
    List<TurnoDocument> findByHoraAtencionBetween(LocalDateTime inicio, LocalDateTime fin);
    List<TurnoDocument> findByBarberoId(String barberoId);
}
