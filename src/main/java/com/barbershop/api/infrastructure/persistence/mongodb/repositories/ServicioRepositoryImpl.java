package com.barbershop.api.infrastructure.persistence.mongodb.repositories;

import org.springframework.stereotype.Repository;
import com.barbershop.api.domain.entities.Servicio;
import com.barbershop.api.domain.repositories.ServicioRepository;
import com.barbershop.api.infrastructure.persistence.mongodb.documents.ServicioDocument;
import com.barbershop.api.infrastructure.persistence.spring.ServicioSpringRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class ServicioRepositoryImpl implements ServicioRepository {
    
    private final ServicioSpringRepository springRepository;
    
    public ServicioRepositoryImpl(ServicioSpringRepository springRepository) {
        this.springRepository = springRepository;
    }
    
    @Override
    public Optional<Servicio> findById(String id) {
        return springRepository.findById(id)
                .map(this::toDomain);
    }
    
    @Override
    public List<Servicio> findAll() {
        return springRepository.findAll()
                .stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }
    
    @Override
    public Servicio save(Servicio servicio) {
        ServicioDocument document = toDocument(servicio);
        ServicioDocument saved = springRepository.save(document);
        return toDomain(saved);
    }
    
    private ServicioDocument toDocument(Servicio servicio) {
        return new ServicioDocument(
            servicio.getId(),
            servicio.getNombre(),
            servicio.getDescripcion(),
            servicio.getPrecioBase(),
            servicio.getDuracionEstimada()
        );
    }
    
    private Servicio toDomain(ServicioDocument document) {
        return new Servicio(
            document.getId(),
            document.getNombre(),
            document.getDescripcion(),
            document.getPrecioBase(),
            document.getDuracionEstimada()
        );
    }
}