package com.barbershop.api.infrastructure.persistence.spring;

import com.barbershop.api.infrastructure.persistence.mongodb.documents.ProductoDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 * Repositorio Spring Data MongoDB para ProductoDocument.
 * Proporciona operaciones CRUD y consultas personalizadas.
 */
@Repository
public interface ProductoSpringRepository
        extends MongoRepository<ProductoDocument, String> {

    /**
     * Busca un producto por su nombre.
     *
     * @param nombre Nombre del producto.
     * @return Optional con el producto si existe.
     */
    Optional<ProductoDocument> findByNombre(String nombre);

    /**
     * Busca productos por proveedor.
     *
     * @param proveedor Nombre del proveedor.
     * @return Lista de productos del proveedor.
     */
    List<ProductoDocument> findByProveedor(String proveedor);

    /**
     * Busca productos con cantidad disponible menor o igual al umbral.
     *
     * @param umbral Umbral de stock bajo.
     * @return Lista de productos con stock bajo.
     */
    @Query("{ 'cantidadDisponible': { $lte: ?0 } }")
    List<ProductoDocument> findByStockBajo(Integer umbral);

    /**
     * Verifica si existe un producto con el nombre especificado.
     *
     * @param nombre Nombre del producto.
     * @return true si existe, false en caso contrario.
     */
    boolean existsByNombre(String nombre);
}
