package com.barbershop.api.service.interactors;

import org.springframework.stereotype.Component;

import com.barbershop.api.domain.entities.Usuario;
import com.barbershop.api.domain.repositories.UsuarioRepository;
import com.barbershop.api.service.dtos.UsuarioRegisterDTO;
import com.barbershop.api.service.dtos.UsuarioResponseDTO;

import lombok.AllArgsConstructor;

/**
 * Use case para registrar un nuevo usuario en el sistema.
 */
@Component
@AllArgsConstructor
public class UsuarioRegistroInteractor {

    private final UsuarioRepository usuarioRepository;

    /**
     * Registra un nuevo usuario.
     *
     * @param dto datos de registro del usuario
     * @return DTO de respuesta con los datos del usuario creado
     */
    public UsuarioResponseDTO ejecutar(final UsuarioRegisterDTO dto) {
        final Usuario usuario = new Usuario(
                dto.getDni(),
                dto.getNombre(),
                dto.getEmail(),
                dto.getPassword(),
                dto.getRole()
        );

        final Usuario usuarioGuardado = usuarioRepository.save(usuario);

        return mapearAResponse(usuarioGuardado);
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
