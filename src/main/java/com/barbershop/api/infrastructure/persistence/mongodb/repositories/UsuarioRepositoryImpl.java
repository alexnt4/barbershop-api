package com.barbershop.api.infrastructure.persistence.mongodb.repositories;

import org.springframework.stereotype.Repository;

import com.barbershop.api.domain.entities.Usuario;
import com.barbershop.api.domain.repositories.UsuarioRepository;
import com.barbershop.api.infrastructure.persistence.mongodb.mappers.UsuarioMapper;
import com.barbershop.api.infrastructure.persistence.spring.UsuarioSpringRepository;

import java.util.List;
import java.util.Optional;

/**
 * Implementación del repositorio Usuario usando MongoDB.
 * Implementa la interfaz del dominio (UsuarioRepository) usando Spring Data.
 */
@Repository
public class UsuarioRepositoryImpl implements UsuarioRepository {

    private final UsuarioSpringRepository springRepository;
    private final UsuarioMapper mapper;

    public UsuarioRepositoryImpl(final UsuarioSpringRepository springRepository, final UsuarioMapper mapper) {
        this.springRepository = springRepository;
        this.mapper = mapper;
    }

    @Override
    public Usuario save(final Usuario usuario) {
        final var document = mapper.toDocument(usuario);
        final var saved = springRepository.save(document);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Usuario> findByEmail(final String email) {
        final var document = springRepository.findByEmail(email);
        return Optional.ofNullable(document).map(mapper::toDomain);
    }

    @Override
    public Optional<Usuario> findByDni(final String dni) {
        return springRepository.findById(dni).map(mapper::toDomain);
    }

    @Override
    public List<Usuario> findAll() {
        return springRepository.findAll().stream()
                .map(mapper::toDomain)
                .collect(java.util.stream.Collectors.toList());
    }

    @Override
    public void deleteByDni(final String dni) {
        springRepository.deleteById(dni);
    }
}
