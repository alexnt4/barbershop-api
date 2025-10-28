package com.barbershop.api.infrastructure.persistence.mongodb.repositories;

import org.springframework.stereotype.Repository;

import com.barbershop.api.domain.entities.especializaciones.Cliente;
import com.barbershop.api.domain.repositories.ClienteRepository;
import com.barbershop.api.infrastructure.persistence.mongodb.mappers.ClienteMapper;
import com.barbershop.api.infrastructure.persistence.spring.ClienteSpringRepository;

import java.util.Optional;

/**
 * Implementación del repositorio Cliente usando MongoDB.
 * Implementa la interfaz del dominio (ClienteRepository) usando Spring Data.
 */
@Repository
public class ClienteRepositoryImpl implements ClienteRepository {

    private final ClienteSpringRepository springRepository;
    private final ClienteMapper mapper;

    public ClienteRepositoryImpl(final ClienteSpringRepository springRepository, final ClienteMapper mapper) {
        this.springRepository = springRepository;
        this.mapper = mapper;
    }

    @Override
    public Cliente save(final Cliente cliente) {
        final var document = mapper.toDocument(cliente);
        final var saved = springRepository.save(document);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Cliente> findByEmail(final String email) {
        final var document = springRepository.findByEmail(email);
        return Optional.ofNullable(document).map(mapper::toDomain);
    }

    @Override
    public Optional<Cliente> findByDni(final String dni) {
        return springRepository.findById(dni).map(mapper::toDomain);
    }
}
