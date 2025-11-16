package com.barbershop.api.infrastructure.persistence.mongodb.documents;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetalleProductoDocument {
    private String productoId;
    private String productoNombre;
    private int cantidad;
    private double precioVenta;
    private double subtotal;
}