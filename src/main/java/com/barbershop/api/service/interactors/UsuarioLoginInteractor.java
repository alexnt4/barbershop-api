package com.barbershop.api.service.interactors;

import org.springframework.stereotype.Component;

import com.barbershop.api.domain.entities.Usuario;
import com.barbershop.api.domain.repositories.UsuarioRepository;
import com.barbershop.api.service.dtos.UsuarioResponseDTO;
import com.barbershop.api.service.exceptions.UsuarioNoEncontradoException;

import lombok.AllArgsConstructor;

/**
 * Use case para login (autenticación) de un usuario.
 * Verifica que exista el usuario con el email y contraseña proporcionados.
 */
@Component
@AllArgsConstructor
public class UsuarioLoginInteractor {

    private final UsuarioRepository usuarioRepository;

    /**
     * Intenta autenticar un usuario con email y contraseña.
     *
     * @param email correo electrónico del usuario
     * @param password contraseña en texto plano
     * @return DTO de respuesta si la autenticación es exitosa
     * @throws UsuarioNoEncontradoException si el usuario no existe o contraseña es incorrecta
     */
    public UsuarioResponseDTO ejecutar(final String email, final String password) {
        final Usuario usuario = usuarioRepository.findByEmail(email)
                .stream()
                .findFirst()
                .orElseThrow(() -> new UsuarioNoEncontradoException("Usuario no encontrado con email: " + email));

        // Validar contraseña (comparar con value object Password)
        if (!usuario.getPassword().getValue().equals(password)) {
            throw new UsuarioNoEncontradoException("Contraseña incorrecta");
        }

        return mapearAResponse(usuario);
    }

    private UsuarioResponseDTO mapearAResponse(final Usuario usuario) {
        return new UsuarioResponseDTO(
                usuario.getDni(),
                usuario.getNombre(),
                usuario.getEmail().getValue(),
                usuario.getRole().name()
        );
    }
}
