package com.barbershop.api.infrastructure.persistence.mongodb.repositories;

import org.springframework.stereotype.Repository;
import com.barbershop.api.domain.entities.Venta;
import com.barbershop.api.domain.repositories.VentaRepository;
import com.barbershop.api.infrastructure.persistence.mongodb.mappers.VentaMapper;
import com.barbershop.api.infrastructure.persistence.spring.VentaSpringRepository;
import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;

@Repository
public class VentaRepositoryImpl implements VentaRepository {

    private final VentaSpringRepository springRepository;
    private final VentaMapper mapper;

    public VentaRepositoryImpl(final VentaSpringRepository springRepository,
                              final VentaMapper mapper) {
        this.springRepository = springRepository;
        this.mapper = mapper;
    }

    @Override
    public Venta save(final Venta venta) {
        final var document = mapper.toDocument(venta);
        final var saved = springRepository.save(document);
        return mapper.toDomain(saved); // Sin parámetros extra
    }

    @Override
    public Optional<Venta> findById(final String id) {
        return springRepository.findById(id)
                .map(mapper::toDomain); // Sin parámetros extra
    }

    @Override
    public List<Venta> findByClienteId(final String clienteId) {
        return springRepository.findByClienteId(clienteId)
                .stream()
                .map(mapper::toDomain) // Sin parámetros extra
                .collect(java.util.stream.Collectors.toList());
    }

    @Override
    public List<Venta> findByBarberoId(final String barberoId) {
        return springRepository.findByBarberoId(barberoId)
                .stream()
                .map(mapper::toDomain) // Sin parámetros extra
                .collect(java.util.stream.Collectors.toList());
    }

    @Override
    public List<Venta> findByFechaBetween(final LocalDateTime inicio, final LocalDateTime fin) {
        return springRepository.findByFechaBetween(inicio, fin)
                .stream()
                .map(mapper::toDomain) // Sin parámetros extra
                .collect(java.util.stream.Collectors.toList());
    }

    @Override
    public List<Venta> findAll() {
        return springRepository.findAll()
                .stream()
                .map(mapper::toDomain) // Sin parámetros extra
                .collect(java.util.stream.Collectors.toList());
    }

    @Override
    public void deleteById(final String id) {
        springRepository.deleteById(id);
    }
}