package com.barbershop.api.service.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para registrar un nuevo barbero.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BarberoRegisterDTO {

    private String dni;
    private String nombre;
    private String email;
    private String password;
}
