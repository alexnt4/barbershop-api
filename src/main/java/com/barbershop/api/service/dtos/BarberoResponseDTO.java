package com.barbershop.api.service.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para la respuesta de barbero (salida).
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BarberoResponseDTO {

    private String dni;
    private String nombre;
    private String email;
    private double rating;
}
