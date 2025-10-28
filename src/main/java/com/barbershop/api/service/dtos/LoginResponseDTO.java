package com.barbershop.api.service.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para la respuesta de login con token JWT.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDTO {
    
    private String token;
    private String tipo = "Bearer";
    private UsuarioResponseDTO usuario;

    public LoginResponseDTO(final String token, final UsuarioResponseDTO usuario) {
        this.token = token;
        this.usuario = usuario;
    }
}
