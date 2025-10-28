package com.barbershop.api.infrastructure.persistence.mongodb.repositories;

import org.springframework.stereotype.Repository;

import com.barbershop.api.domain.entities.especializaciones.Administrador;
import com.barbershop.api.domain.repositories.AdministradorRepository;
import com.barbershop.api.infrastructure.persistence.mongodb.mappers.AdministradorMapper;
import com.barbershop.api.infrastructure.persistence.spring.AdministradorSpringRepository;

import java.util.Optional;

/**
 * Implementación del repositorio Administrador usando MongoDB.
 * Implementa la interfaz del dominio (AdministradorRepository) usando Spring Data.
 */
@Repository
public class AdministradorRepositoryImpl implements AdministradorRepository {

    private final AdministradorSpringRepository springRepository;
    private final AdministradorMapper mapper;

    public AdministradorRepositoryImpl(final AdministradorSpringRepository springRepository,
                                       final AdministradorMapper mapper) {
        this.springRepository = springRepository;
        this.mapper = mapper;
    }

    @Override
    public Administrador save(final Administrador administrador) {
        final var document = mapper.toDocument(administrador);
        final var saved = springRepository.save(document);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Administrador> findByEmail(final String email) {
        final var document = springRepository.findByEmail(email);
        return Optional.ofNullable(document).map(mapper::toDomain);
    }

    @Override
    public Optional<Administrador> findByDni(final String dni) {
        return springRepository.findById(dni).map(mapper::toDomain);
    }
}
