package com.barbershop.api.infrastructure.persistence.mongodb.repositories;

import com.barbershop.api.domain.entities.Producto;
import com.barbershop.api.domain.repositories.ProductoRepository;
import com.barbershop.api.infrastructure.persistence.mongodb.documents.ProductoDocument;
import com.barbershop.api.infrastructure.persistence.mongodb.mappers.ProductoMapper;
import com.barbershop.api.infrastructure.persistence.spring.ProductoSpringRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementación de ProductoRepository usando MongoDB.
 * Actúa como adaptador entre el dominio y la infraestructura.
 */
@Repository
public class ProductoRepositoryImpl implements ProductoRepository {

    /**
     * Repositorio Spring Data MongoDB.
     */
    private final ProductoSpringRepository springRepository;

    /**
     * Constructor con inyección de dependencias.
     *
     * @param springRepositoryParam Repositorio de Spring Data.
     */
    public ProductoRepositoryImpl(
            final ProductoSpringRepository springRepositoryParam) {
        this.springRepository = springRepositoryParam;
    }

    @Override
    public Producto save(final Producto producto) {
        ProductoDocument document = ProductoMapper.toDocument(producto);
        ProductoDocument savedDocument = springRepository.save(document);
        return ProductoMapper.toDomain(savedDocument);
    }

    @Override
    public Optional<Producto> findById(final String id) {
        return springRepository.findById(id)
                .map(ProductoMapper::toDomain);
    }

    @Override
    public Optional<Producto> findByNombre(final String nombre) {
        return springRepository.findByNombre(nombre)
                .map(ProductoMapper::toDomain);
    }

    @Override
    public List<Producto> findAll() {
        return springRepository.findAll()
                .stream()
                .map(ProductoMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Producto> findByProveedor(final String proveedor) {
        return springRepository.findByProveedor(proveedor)
                .stream()
                .map(ProductoMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Producto> findByStockBajo(final Integer umbral) {
        return springRepository.findByStockBajo(umbral)
                .stream()
                .map(ProductoMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(final String id) {
        springRepository.deleteById(id);
    }

    @Override
    public boolean existsByNombre(final String nombre) {
        return springRepository.existsByNombre(nombre);
    }
}
