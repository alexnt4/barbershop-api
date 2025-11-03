package com.barbershop.api.infrastructure.persistence.mongodb.documents;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Documento MongoDB para la entidad Producto.
 * Representa la estructura de almacenamiento en la base de datos.
 */
@Document(collection = "productos")
public class ProductoDocument {
    /**
     * Identificador único del producto en MongoDB.
     */
    @Id
    private String id;

    /**
     * Nombre del producto (indexado para búsquedas rápidas).
     */
    @Indexed(unique = true)
    private String nombre;

    /**
     * Proveedor del producto.
     */
    private String proveedor;

    /**
     * Cantidad disponible en inventario.
     */
    private Integer cantidadDisponible;

    /**
     * Precio unitario del producto.
     */
    private BigDecimal precioUnitario;

    /**
     * Fecha de última actualización del producto.
     */
    private LocalDateTime fechaActualizacion;

    /**
     * Constructor vacío requerido por Spring Data MongoDB.
     */
    public ProductoDocument() {
    }

    /**
     * Constructor completo.
     *
     * @param idParam                 ID del producto.
     * @param nombreParam             Nombre del producto.
     * @param proveedorParam          Proveedor.
     * @param cantidadDisponibleParam Cantidad disponible.
     * @param precioUnitarioParam     Precio unitario.
     * @param fechaActualizacionParam Fecha de actualización.
     */
    public ProductoDocument(final String idParam, final String nombreParam,
            final String proveedorParam,
            final Integer cantidadDisponibleParam,
            final BigDecimal precioUnitarioParam,
            final LocalDateTime fechaActualizacionParam) {
        this.id = idParam;
        this.nombre = nombreParam;
        this.proveedor = proveedorParam;
        this.cantidadDisponible = cantidadDisponibleParam;
        this.precioUnitario = precioUnitarioParam;
        this.fechaActualizacion = fechaActualizacionParam;
    }

    // Getters y Setters
    public String getId() {
        return id;
    }

    public void setId(final String idParam) {
        this.id = idParam;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(final String nombreParam) {
        this.nombre = nombreParam;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(final String proveedorParam) {
        this.proveedor = proveedorParam;
    }

    public Integer getCantidadDisponible() {
        return cantidadDisponible;
    }

    public void setCantidadDisponible(final Integer cantidadParam) {
        this.cantidadDisponible = cantidadParam;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(final BigDecimal precioParam) {
        this.precioUnitario = precioParam;
    }

    public LocalDateTime getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(final LocalDateTime fechaParam) {
        this.fechaActualizacion = fechaParam;
    }
}
