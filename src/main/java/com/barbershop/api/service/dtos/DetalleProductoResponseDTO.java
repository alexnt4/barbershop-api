package com.barbershop.api.service.dtos;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class DetalleProductoResponseDTO {
    private String productoId;
    private String productoNombre;
    private int cantidad;
    private BigDecimal precioVenta;
    private BigDecimal subtotal;
}