package com.barbershop.api.service.interactors;

import org.springframework.stereotype.Component;

import com.barbershop.api.domain.repositories.TurnoRepository;
import com.barbershop.api.domain.value_objects.TurnoEstado;
import com.barbershop.api.service.exceptions.TurnoNoEncontradoException;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class FinalizarTurnoInteractor {

    final TurnoRepository turnoRepository;

    public void ejecutar(final String turnoId) {
        var turnoOpt = turnoRepository.findById(turnoId);
        if (!turnoOpt.isPresent()) {
            throw new TurnoNoEncontradoException("Turno no encontrado con ID: " + turnoId);
        }
        var turno = turnoOpt.get();
        turno.setEstado(TurnoEstado.COMPLETADO);
        turnoRepository.save(turno);
    }
    
    
}
