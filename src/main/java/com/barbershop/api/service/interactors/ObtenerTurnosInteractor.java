package com.barbershop.api.service.interactors;

import java.util.List;

import org.springframework.stereotype.Component;

import com.barbershop.api.domain.entities.Turno;
import com.barbershop.api.domain.repositories.TurnoRepository;
import com.barbershop.api.domain.value_objects.TurnoEstado;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ObtenerTurnosInteractor {

    final TurnoRepository turnoRepository;

    public List<Turno> ejecutar(final TurnoEstado estado) {
        return turnoRepository.findAll().stream()
                .filter(turno -> estado == null ? true : turno.getEstado() == estado)
                .toList();
    }
}
