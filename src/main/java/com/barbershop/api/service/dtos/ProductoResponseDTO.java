package com.barbershop.api.service.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO de respuesta para productos.
 * Contiene toda la información del producto para mostrar al cliente.
 */
public class ProductoResponseDTO {
    /**
     * ID del producto.
     */
    private String id;

    /**
     * Nombre del producto.
     */
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
     * Valor total del inventario (cantidad * precio).
     */
    private BigDecimal valorInventario;

    /**
     * Fecha de última actualización.
     */
    private LocalDateTime fechaActualizacion;

    /**
     * Precio de compra del producto (nuevo campo).
     */
    private BigDecimal precioCompra;

    /**
     * ID del proveedor asociado (nuevo campo).
     */
    private String proveedorId;

    /**
     * Constructor vacío.
     */
    public ProductoResponseDTO() {
    }

    /**
     * Constructor completo.
     *
     * @param idParam                 ID del producto.
     * @param nombreParam             Nombre del producto.
     * @param proveedorParam          Proveedor.
     * @param cantidadDisponibleParam Cantidad disponible.
     * @param precioUnitarioParam     Precio unitario.
     * @param valorInventarioParam    Valor total del inventario.
     * @param fechaActualizacionParam Fecha de actualización.
     */
    public ProductoResponseDTO(final String idParam,
        final String nombreParam,
        final String proveedorParam,
        final Integer cantidadDisponibleParam,
        final BigDecimal precioUnitarioParam,
        final BigDecimal valorInventarioParam,
        final LocalDateTime fechaActualizacionParam,
        final String proveedorIdParam) {
    
    this.id = idParam;  
    this.nombre = nombreParam;
    this.proveedor = proveedorParam;
    this.cantidadDisponible = cantidadDisponibleParam;
    this.precioUnitario = precioUnitarioParam;
    this.valorInventario = valorInventarioParam;
    this.fechaActualizacion = fechaActualizacionParam;
    this.proveedorId = proveedorIdParam;
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

    public BigDecimal getValorInventario() {
        return valorInventario;
    }

    public void setValorInventario(final BigDecimal valorParam) {
        this.valorInventario = valorParam;
    }

    public LocalDateTime getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(final LocalDateTime fechaParam) {
        this.fechaActualizacion = fechaParam;
    }

    public BigDecimal getPrecioCompra() {
        return precioCompra;
    }

    public void setPrecioCompra(final BigDecimal precioCompraParam) {
        this.precioCompra = precioCompraParam;
    }

    public String getProveedorId() {
        return proveedorId;
    }

    public void setProveedorId(final String proveedorIdParam) {
        this.proveedorId = proveedorIdParam;
    }
}
