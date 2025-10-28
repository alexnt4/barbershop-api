package com.barbershop.api.service.interactors;

import org.springframework.stereotype.Component;

import com.barbershop.api.domain.entities.Usuario;
import com.barbershop.api.domain.repositories.UsuarioRepository;
import com.barbershop.api.service.dtos.UsuarioResponseDTO;
import com.barbershop.api.service.exceptions.UsuarioNoEncontradoException;

import lombok.AllArgsConstructor;

/**
 * Use case para obtener un usuario por su DNI.
 */
@Component
@AllArgsConstructor
public class UsuarioGetByIdInteractor {

    private final UsuarioRepository usuarioRepository;

    /**
     * Obtiene un usuario por su DNI.
     *
     * @param dni identificador del usuario
     * @return DTO de respuesta con los datos del usuario
     * @throws UsuarioNoEncontradoException si el usuario no existe
     */
    public UsuarioResponseDTO ejecutar(final String dni) {
        final Usuario usuario = usuarioRepository.findByDni(dni)
                .orElseThrow(() -> new UsuarioNoEncontradoException("Usuario no encontrado con DNI: " + dni));

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
