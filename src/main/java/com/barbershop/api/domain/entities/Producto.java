package com.barbershop.api.domain.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * La clase Producto representa un producto del inventario de la barbería.
 * Puede ser un producto vendido o utilizado internamente (cera, champú, gel,
 * etc).
 */
public class Producto {
    /**
     * Identificador único del producto (generado por MongoDB).
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
     * Fecha de última actualización del producto.
     */
    private LocalDateTime fechaActualizacion;

    /**
     * Constructor completo de la clase Producto.
     *
     * @param idParam                 Identificador único del producto.
     * @param nombreParam             Nombre del producto.
     * @param proveedorParam          Proveedor del producto.
     * @param cantidadDisponibleParam Cantidad disponible en inventario.
     * @param precioUnitarioParam     Precio unitario del producto.
     * @param fechaActualizacionParam Fecha de última actualización.
     */
    public Producto(final String idParam, final String nombreParam,
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

    /**
     * Constructor sin ID (para nuevos productos).
     *
     * @param nombreParam             Nombre del producto.
     * @param proveedorParam          Proveedor del producto.
     * @param cantidadDisponibleParam Cantidad disponible en inventario.
     * @param precioUnitarioParam     Precio unitario del producto.
     */
    public Producto(final String nombreParam, final String proveedorParam,
            final Integer cantidadDisponibleParam,
            final BigDecimal precioUnitarioParam) {
        this.nombre = nombreParam;
        this.proveedor = proveedorParam;
        this.cantidadDisponible = cantidadDisponibleParam;
        this.precioUnitario = precioUnitarioParam;
        this.fechaActualizacion = LocalDateTime.now();
    }

    /**
     * Registra la entrada de productos al inventario.
     *
     * @param cantidad Cantidad a agregar al inventario.
     * @throws IllegalArgumentException si la cantidad es negativa.
     */
    public void registrarEntrada(final Integer cantidad) {
        if (cantidad < 0) {
            throw new IllegalArgumentException(
                    "La cantidad de entrada no puede ser negativa");
        }
        this.cantidadDisponible += cantidad;
        this.fechaActualizacion = LocalDateTime.now();
    }

    /**
     * Registra la salida de productos del inventario.
     *
     * @param cantidad Cantidad a retirar del inventario.
     * @throws IllegalArgumentException si la cantidad es negativa o
     *                                  excede la cantidad disponible.
     */
    public void registrarSalida(final Integer cantidad) {
        if (cantidad < 0) {
            throw new IllegalArgumentException(
                    "La cantidad de salida no puede ser negativa");
        }
        if (cantidad > this.cantidadDisponible) {
            throw new IllegalArgumentException(
                    "No hay suficiente inventario. Disponible: "
                            + this.cantidadDisponible);
        }
        this.cantidadDisponible -= cantidad;
        this.fechaActualizacion = LocalDateTime.now();
    }

    /**
     * Actualiza el precio unitario del producto.
     *
     * @param nuevoPrecio Nuevo precio unitario.
     * @throws IllegalArgumentException si el precio es negativo.
     */
    public void actualizarPrecio(final BigDecimal nuevoPrecio) {
        if (nuevoPrecio.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(
                    "El precio no puede ser negativo");
        }
        this.precioUnitario = nuevoPrecio;
        this.fechaActualizacion = LocalDateTime.now();
    }

    /**
     * Verifica si el producto tiene stock bajo.
     *
     * @param umbral Umbral mínimo de stock.
     * @return true si la cantidad disponible es menor o igual al umbral.
     */
    public boolean tieneStockBajo(final Integer umbral) {
        return this.cantidadDisponible <= umbral;
    }

    /**
     * Calcula el valor total del inventario de este producto.
     *
     * @return Valor total (cantidad * precio unitario).
     */
    public BigDecimal calcularValorInventario() {
        return this.precioUnitario.multiply(
                BigDecimal.valueOf(this.cantidadDisponible));
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getProveedor() {
        return proveedor;
    }

    public Integer getCantidadDisponible() {
        return cantidadDisponible;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public LocalDateTime getFechaActualizacion() {
        return fechaActualizacion;
    }

    // Setters
    public void setId(final String idParam) {
        this.id = idParam;
    }

    public void setNombre(final String nombreParam) {
        this.nombre = nombreParam;
        this.fechaActualizacion = LocalDateTime.now();
    }

    public void setProveedor(final String proveedorParam) {
        this.proveedor = proveedorParam;
        this.fechaActualizacion = LocalDateTime.now();
    }

    public void setFechaActualizacion(final LocalDateTime fechaParam) {
        this.fechaActualizacion = fechaParam;
    }
}
