package com.barbershop.api.service.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para registrar un nuevo usuario.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioRegisterDTO {

    private String dni;
    private String nombre;
    private String email;
    private String password;
    private String role;
}
