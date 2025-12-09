package com.barbershop.api.service.dtos;

import lombok.Data;

@Data
public class DetalleServicioResponseDTO {
    private String servicioId;
    private String servicioNombre;
    private double precio;
    private int duracion;
}