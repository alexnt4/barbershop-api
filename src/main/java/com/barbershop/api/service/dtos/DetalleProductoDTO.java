package com.barbershop.api.service.dtos;

import lombok.Data;

@Data
public class DetalleProductoDTO {
    private String productoId;
    private int cantidad;
}