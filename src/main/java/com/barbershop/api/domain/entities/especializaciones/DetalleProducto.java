package com.barbershop.api.domain.entities.especializaciones;

import com.barbershop.api.domain.entities.Producto;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

/**
 * Representa un producto vendido en una venta con su cantidad y precio.
 */
@Getter
@Setter
public class DetalleProducto {
    private Producto producto;
    private int cantidad;
    private BigDecimal precioVenta; 
    private BigDecimal subtotal;

    public DetalleProducto(final Producto productoParam, final int cantidadParam, final BigDecimal precioVentaParam) {
        this.producto = productoParam;
        this.cantidad = cantidadParam;
        this.precioVenta = precioVentaParam;
        this.subtotal = precioVentaParam.multiply(BigDecimal.valueOf(cantidadParam));
    }
}