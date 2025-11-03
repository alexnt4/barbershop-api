package com.barbershop.api.service.dtos;

/**
 * DTO para registrar movimientos de inventario (entrada/salida).
 */
public class MovimientoInventarioDTO {
    /**
     * ID del producto.
     */
    private String productoId;

    /**
     * Cantidad del movimiento (positiva para entrada, procesada como salida).
     */
    private Integer cantidad;

    /**
     * Tipo de movimiento: "ENTRADA" o "SALIDA".
     */
    private String tipoMovimiento;

    /**
     * Motivo del movimiento (opcional).
     */
    private String motivo;

    /**
     * Constructor vacío.
     */
    public MovimientoInventarioDTO() {
    }

    /**
     * Constructor con parámetros.
     *
     * @param productoIdParam     ID del producto.
     * @param cantidadParam       Cantidad del movimiento.
     * @param tipoMovimientoParam Tipo: "ENTRADA" o "SALIDA".
     * @param motivoParam         Motivo del movimiento.
     */
    public MovimientoInventarioDTO(final String productoIdParam,
            final Integer cantidadParam,
            final String tipoMovimientoParam,
            final String motivoParam) {
        this.productoId = productoIdParam;
        this.cantidad = cantidadParam;
        this.tipoMovimiento = tipoMovimientoParam;
        this.motivo = motivoParam;
    }

    // Getters y Setters
    public String getProductoId() {
        return productoId;
    }

    public void setProductoId(final String productoIdParam) {
        this.productoId = productoIdParam;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(final Integer cantidadParam) {
        this.cantidad = cantidadParam;
    }

    public String getTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(final String tipoParam) {
        this.tipoMovimiento = tipoParam;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(final String motivoParam) {
        this.motivo = motivoParam;
    }
}
