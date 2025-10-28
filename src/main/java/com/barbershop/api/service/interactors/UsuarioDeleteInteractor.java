package com.barbershop.api.service.interactors;

import org.springframework.stereotype.Component;

import com.barbershop.api.domain.repositories.UsuarioRepository;
import com.barbershop.api.service.exceptions.UsuarioNoEncontradoException;

import lombok.AllArgsConstructor;

/**
 * Use case para eliminar un usuario del sistema.
 */
@Component
@AllArgsConstructor
public class UsuarioDeleteInteractor {

    private final UsuarioRepository usuarioRepository;

    /**
     * Elimina un usuario por su DNI.
     *
     * @param dni identificador del usuario a eliminar
     * @throws UsuarioNoEncontradoException si el usuario no existe
     */
    public void ejecutar(final String dni) {
        // Verificar que el usuario existe antes de eliminarlo
        usuarioRepository.findByDni(dni)
                .orElseThrow(() -> new UsuarioNoEncontradoException("Usuario no encontrado con DNI: " + dni));

        usuarioRepository.deleteByDni(dni);
    }
}
