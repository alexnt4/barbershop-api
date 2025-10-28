package com.barbershop.api.transport.http.controllers;

import com.barbershop.api.infrastructure.security.JwtProvider;
import com.barbershop.api.service.dtos.LoginRequestDTO;
import com.barbershop.api.service.dtos.LoginResponseDTO;
import com.barbershop.api.service.dtos.UsuarioResponseDTO;
import com.barbershop.api.service.interactors.UsuarioLoginInteractor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller para autenticación de usuarios.
 */
@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
@Tag(name = "Autenticación", description = "Endpoints para login y autenticación")
public class AuthController {

    private final UsuarioLoginInteractor loginInteractor;
    private final JwtProvider jwtProvider;

    /**
     * Endpoint para login de usuarios.
     *
     * @param loginRequest credenciales de login
     * @return token JWT y datos del usuario
     */
    @PostMapping("/login")
    @Operation(summary = "Login de usuario", description = "Autentica un usuario y retorna un token JWT")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody final LoginRequestDTO loginRequest) {
        final UsuarioResponseDTO usuario = loginInteractor.ejecutar(
                loginRequest.getEmail(),
                loginRequest.getPassword()
        );

        final String token = jwtProvider.generateToken(
                usuario.getDni(),
                usuario.getEmail(),
                usuario.getRole()
        );

        final LoginResponseDTO response = new LoginResponseDTO(token, usuario);

        return ResponseEntity.ok(response);
    }
}
