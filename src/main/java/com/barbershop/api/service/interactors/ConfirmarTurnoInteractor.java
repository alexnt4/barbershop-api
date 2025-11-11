package com.barbershop.api.service.interactors;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.barbershop.api.domain.entities.Turno;
import com.barbershop.api.domain.repositories.TurnoRepository;
import com.barbershop.api.domain.value_objects.TurnoEstado;
import com.barbershop.api.service.exceptions.TurnoConflictoException;
import com.barbershop.api.service.exceptions.TurnoNoEncontradoException;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ConfirmarTurnoInteractor {

    final TurnoRepository turnoRepository;

    public Turno ejecutar(final String turnoId) {
        var turnoOpt = turnoRepository.findById(turnoId);
        if (!turnoOpt.isPresent()) {
            throw new TurnoNoEncontradoException("Turno no encontrado con ID: " + turnoId);
        }
        LocalDateTime now = LocalDateTime.now();
        var turno = turnoOpt.get();

        if (turno.getHoraAtencion().minusMinutes(30).isAfter(now) || now.isAfter(turno.getHoraAtencion().plusMinutes(5))) {
            throw new TurnoConflictoException("No se puede confirmar el turno en este momento");
        }
        turno.setEstado(TurnoEstado.CONFIRMADO);
        var  turnoGuardado = turnoRepository.save(turno);
        return turnoGuardado;
    }
    

  
 }
