package com.barbershop.api.infrastructure.persistence.mongodb.mappers;

import com.barbershop.api.domain.entities.Producto;
import com.barbershop.api.infrastructure.persistence.mongodb.documents.ProductoDocument;

/**
 * Mapper para convertir entre Producto (entidad de dominio)
 * y ProductoDocument (documento de MongoDB).
 */
public final class ProductoMapper {

    /**
     * Constructor privado para prevenir instanciación.
     */
    private ProductoMapper() {
        throw new UnsupportedOperationException(
                "Esta es una clase utilitaria y no puede ser instanciada");
    }

    /**
     * Convierte un ProductoDocument a Producto (entidad de dominio).
     *
     * @param document Documento de MongoDB.
     * @return Producto del dominio.
     */
    public static Producto toDomain(final ProductoDocument document) {
        if (document == null) {
            return null;
        }

        return new Producto(
                document.getId(),
                document.getNombre(),
                document.getProveedor(),
                document.getCantidadDisponible(),
                document.getPrecioUnitario(),
                document.getFechaActualizacion());
    }

    /**
     * Convierte un Producto (entidad de dominio) a ProductoDocument.
     *
     * @param producto Entidad del dominio.
     * @return Documento de MongoDB.
     */
    public static ProductoDocument toDocument(final Producto producto) {
        if (producto == null) {
            return null;
        }

        return new ProductoDocument(
                producto.getId(),
                producto.getNombre(),
                producto.getProveedor(),
                producto.getCantidadDisponible(),
                producto.getPrecioUnitario(),
                producto.getFechaActualizacion());
    }
}
