package com.barbershop.api.domain.entities;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.List;
import java.math.BigDecimal;

import com.barbershop.api.domain.entities.especializaciones.DetalleProducto;
import com.barbershop.api.domain.entities.especializaciones.DetalleServicio;

/**
 * Representa una venta de productos y/o servicios en la barbería.
 */
@Getter
@Setter
public class Venta {
    private String id;
    private LocalDateTime fecha;
    private Usuario cliente;
    private Usuario barbero;
    private List<DetalleProducto> productos;
    private List<DetalleServicio> servicios;
    private BigDecimal montoTotal; 
    private String metodoPago;
    private String estado;

    public Venta(final String idParam, final LocalDateTime fechaParam, final Usuario clienteParam, 
                 final Usuario barberoParam, final List<DetalleProducto> productosParam,
                 final List<DetalleServicio> serviciosParam, final BigDecimal montoTotalParam,
                 final String metodoPagoParam, final String estadoParam) {
        this.id = idParam;
        this.fecha = fechaParam;
        this.cliente = clienteParam;
        this.barbero = barberoParam;
        this.productos = productosParam;
        this.servicios = serviciosParam;
        this.montoTotal = montoTotalParam;
        this.metodoPago = metodoPagoParam;
        this.estado = estadoParam;
    }
}