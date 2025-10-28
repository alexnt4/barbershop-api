package com.barbershop.api.infrastructure.persistence.mongodb.repositories;

import org.springframework.stereotype.Repository;

import com.barbershop.api.domain.entities.especializaciones.Barbero;
import com.barbershop.api.domain.repositories.BarberoRepository;
import com.barbershop.api.infrastructure.persistence.mongodb.mappers.BarberoMapper;
import com.barbershop.api.infrastructure.persistence.spring.BarberoSpringRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementación del repositorio Barbero usando MongoDB.
 * Implementa la interfaz del dominio (BarberoRepository) usando Spring Data.
 */
@Repository
public class BarberoRepositoryImpl implements BarberoRepository {

    private final BarberoSpringRepository springRepository;
    private final BarberoMapper mapper;

    public BarberoRepositoryImpl(final BarberoSpringRepository springRepository, final BarberoMapper mapper) {
        this.springRepository = springRepository;
        this.mapper = mapper;
    }

    @Override
    public Barbero save(final Barbero barbero) {
        final var document = mapper.toDocument(barbero);
        final var saved = springRepository.save(document);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Barbero> findByEmail(final String email) {
        final var document = springRepository.findByEmail(email);
        return Optional.ofNullable(document).map(mapper::toDomain);
    }

    @Override
    public Optional<Barbero> findByDni(final String dni) {
        return springRepository.findById(dni).map(mapper::toDomain);
    }

    @Override
    public List<Barbero> findAll() {
        return springRepository.findAll().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }
}
