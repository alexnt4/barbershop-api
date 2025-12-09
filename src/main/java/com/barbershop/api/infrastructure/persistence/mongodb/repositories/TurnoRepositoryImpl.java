package com.barbershop.api.infrastructure.persistence.mongodb.repositories;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.barbershop.api.domain.entities.Turno;
import com.barbershop.api.domain.repositories.TurnoRepository;
import com.barbershop.api.infrastructure.persistence.mongodb.mappers.TurnoMapper;
import com.barbershop.api.infrastructure.persistence.spring.TurnoSpringRepository;

import lombok.AllArgsConstructor;


@Repository
@AllArgsConstructor
public class TurnoRepositoryImpl  implements TurnoRepository {
    private final TurnoSpringRepository springRepository;
    @Override
    public List<Turno> findAll() {
        return springRepository.findAll()
                .stream()
                .map(TurnoMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Turno> findById(String id) {
        return springRepository.findById(id)
                .map(TurnoMapper::toDomain);
    }

    @Override
    public Turno save(Turno turno) {
        var document = TurnoMapper.toDocument(turno);
        var savedDocument = springRepository.save(document);
        return TurnoMapper.toDomain(savedDocument);
    }

    @Override
    public List<Turno> findByBarberoIdAndHoraAtencionBetween(String barberoId, LocalDateTime inicio,
            LocalDateTime fin) {
        return springRepository.findByBarberoIdAndHoraAtencionBetween(barberoId, inicio, fin)
                .stream()
                .map(TurnoMapper::toDomain)
                .toList();
    }

    @Override
    public List<Turno> findByBarberoIdAndHoraAtencionLessThanAndHoraFinalizacionGreaterThan(String barberoId,
            LocalDateTime horaFinalizacionNueva, LocalDateTime horaAtencionNueva) {
        return springRepository.findByBarberoIdAndHoraAtencionLessThanAndHoraFinalizacionGreaterThan(barberoId, horaFinalizacionNueva, horaAtencionNueva)
                .stream()
                .map(TurnoMapper::toDomain)
                .toList();
    }

    @Override
    public List<Turno> findByHoraAtencionBetween(LocalDateTime inicio, LocalDateTime fin) {
        return springRepository.findByHoraAtencionBetween(inicio, fin)
                .stream()
                .map(TurnoMapper::toDomain)
                .toList();
    }

    @Override
    public List<Turno> findByBarberoId(String barberoId) {
        return springRepository.findByBarberoId(barberoId).stream()
                .map(TurnoMapper::toDomain)
                .toList();
    }
}
