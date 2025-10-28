package com.barbershop.api.service.interactors;

import org.springframework.stereotype.Component;

import com.barbershop.api.domain.entities.Usuario;
import com.barbershop.api.domain.repositories.UsuarioRepository;
import com.barbershop.api.service.dtos.BarberoResponseDTO;
import com.barbershop.api.service.exceptions.BarberoNoEncontradoException;

import lombok.AllArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Use case para obtener la lista de usuarios con rol BARBERO.
 */
@Component
@AllArgsConstructor
public class ObtenerBarberosInteractor {

    private final UsuarioRepository usuarioRepository;

    /**
     * Obtiene todos los usuarios con rol BARBERO.
     *
     * @return lista de DTOs de respuesta con los datos de los barberos
     * @throws BarberoNoEncontradoException si no hay barberos registrados
     */
    public List<BarberoResponseDTO> ejecutar() {
        final List<Usuario> todosLosUsuarios = usuarioRepository.findAll();
        
        final List<Usuario> barberos = todosLosUsuarios.stream()
                .filter(usuario -> "BARBERO".equals(usuario.getRole().name()))
                .collect(Collectors.toList());

        if (barberos.isEmpty()) {
            throw new BarberoNoEncontradoException("No hay barberos registrados en el sistema");
        }

        return barberos.stream()
                .map(this::mapearAResponse)
                .collect(Collectors.toList());
    }

    private BarberoResponseDTO mapearAResponse(final Usuario usuario) {
        return new BarberoResponseDTO(
                usuario.getDni(),
                usuario.getNombre(),
                usuario.getEmail().getValue(),
                0.0  // Por ahora rating en 0, ya que Usuario no tiene rating
        );
    }
}
