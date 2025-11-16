package com.barbershop.api.service.dtos;


import lombok.Data;

@Data
public class ServicioRegisterDTO {
    private String nombre;
    private String descripcion;
    private double precioBase;
    private int duracionEstimada; // en minutos
}
