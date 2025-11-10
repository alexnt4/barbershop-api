package com.barbershop.api.infrastructure.persistence.mongodb.repositories;

import org.springframework.stereotype.Repository;

import com.barbershop.api.domain.entities.Servicio;
import com.barbershop.api.domain.repositories.ServicioRepository;
import com.barbershop.api.infrastructure.persistence.mongodb.mappers.ServicioMapper;
import com.barbershop.api.infrastructure.persistence.spring.ServicioSpringRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class ServicioRepositoryImpl implements ServicioRepository {

    private final ServicioSpringRepository springRepository;
    private final ServicioMapper mapper;

    public ServicioRepositoryImpl(final ServicioSpringRepository springRepository, final ServicioMapper mapper) {
        this.springRepository = springRepository;
        this.mapper = mapper;
    }

    @Override
    public List<Servicio> findAll() {
        return springRepository.findAll().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Servicio> findById(final String id) {
        return springRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Servicio save(final Servicio servicio) {
        final var document = mapper.toDocument(servicio);
        final var saved = springRepository.save(document);
        return mapper.toDomain(saved);
    }

    @Override
    public void deleteById(final String id) {
        springRepository.deleteById(id);
    }
}
