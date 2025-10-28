package com.barbershop.api.service.interactors;

import org.springframework.stereotype.Component;

import com.barbershop.api.domain.entities.especializaciones.Barbero;
import com.barbershop.api.domain.repositories.BarberoRepository;
import com.barbershop.api.service.dtos.BarberoRegisterDTO;
import com.barbershop.api.service.dtos.BarberoResponseDTO;

import lombok.AllArgsConstructor;

/**
 * Use case para registrar un nuevo barbero en el sistema.
 */
@Component
@AllArgsConstructor
public class BarberoRegistroInteractor {

    private final BarberoRepository barberoRepository;

    /**
     * Registra un nuevo barbero.
     *
     * @param dto datos de registro del barbero
     * @return DTO de respuesta con los datos del barbero creado
     */
    public BarberoResponseDTO ejecutar(final BarberoRegisterDTO dto) {
        final Barbero barbero = new Barbero(
                dto.getDni(),
                dto.getNombre(),
                dto.getEmail(),
                dto.getPassword()
        );

        final Barbero barberoGuardado = barberoRepository.save(barbero);

        return new BarberoResponseDTO(
                barberoGuardado.getDni(),
                barberoGuardado.getNombre(),
                barberoGuardado.getEmail().getValue(),
                barberoGuardado.getAverageRating()
        );
    }
}
