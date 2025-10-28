package com.barbershop.api.service.interactors;

import org.springframework.stereotype.Component;

import com.barbershop.api.domain.entities.Usuario;
import com.barbershop.api.domain.repositories.UsuarioRepository;
import com.barbershop.api.service.dtos.UsuarioResponseDTO;
import com.barbershop.api.service.dtos.UsuarioUpdateDTO;
import com.barbershop.api.service.exceptions.UsuarioNoEncontradoException;

import lombok.AllArgsConstructor;

/**
 * Use case para actualizar los datos de un usuario existente.
 */
@Component
@AllArgsConstructor
public class UsuarioUpdateInteractor {

    private final UsuarioRepository usuarioRepository;

    /**
     * Actualiza los datos de un usuario.
     *
     * @param dni identificador del usuario a actualizar
     * @param dto datos nuevos del usuario
     * @return DTO de respuesta con los datos actualizados
     * @throws UsuarioNoEncontradoException si el usuario no existe
     */
    public UsuarioResponseDTO ejecutar(final String dni, final UsuarioUpdateDTO dto) {
        final Usuario usuario = usuarioRepository.findByDni(dni)
                .orElseThrow(() -> new UsuarioNoEncontradoException("Usuario no encontrado con DNI: " + dni));

        // Actualizar campos
        if (dto.getNombre() != null && !dto.getNombre().isBlank()) {
            usuario.setNombre(dto.getNombre());
        }
        if (dto.getEmail() != null && !dto.getEmail().isBlank()) {
            usuario.setEmail(new com.barbershop.api.domain.value_objects.Email(dto.getEmail()));
        }
        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            usuario.setPassword(dto.getPassword());
        }

        final Usuario usuarioActualizado = usuarioRepository.save(usuario);

        return mapearAResponse(usuarioActualizado);
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
