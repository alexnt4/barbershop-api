package com.barbershop.api.service.dtos;

import java.math.BigDecimal;

/**
 * DTO para registrar un nuevo producto en el sistema.
 */
public class ProductoRegisterDTO {
    /**
     * Nombre del producto.
     */
    private String nombre;

    /**
     * Proveedor del producto.
     */
    private String proveedor;

    /**
     * Cantidad inicial disponible.
     */
    private Integer cantidadDisponible;

    /**
     * Precio unitario del producto.
     */
    private BigDecimal precioUnitario;

    /**
     * Constructor vacío.
     */
    public ProductoRegisterDTO() {
    }

    /**
     * Constructor con parámetros.
     *
     * @param nombreParam             Nombre del producto.
     * @param proveedorParam          Proveedor.
     * @param cantidadDisponibleParam Cantidad inicial.
     * @param precioUnitarioParam     Precio unitario.
     */
    public ProductoRegisterDTO(final String nombreParam,
            final String proveedorParam,
            final Integer cantidadDisponibleParam,
            final BigDecimal precioUnitarioParam) {
        this.nombre = nombreParam;
        this.proveedor = proveedorParam;
        this.cantidadDisponible = cantidadDisponibleParam;
        this.precioUnitario = precioUnitarioParam;
    }

    // Getters y Setters
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
}
