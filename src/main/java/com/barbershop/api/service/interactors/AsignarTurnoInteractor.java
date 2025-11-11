package com.barbershop.api.service.interactors;

import java.time.LocalDateTime;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;

import com.barbershop.api.domain.entities.Turno;
import com.barbershop.api.domain.repositories.BarberoRepository;
import com.barbershop.api.domain.repositories.ServicioRepository;
import com.barbershop.api.domain.repositories.TurnoRepository;
import com.barbershop.api.domain.repositories.UsuarioRepository;
import com.barbershop.api.domain.value_objects.TurnoEstado;
import com.barbershop.api.service.exceptions.BarberoNoEncontradoException;
import com.barbershop.api.service.exceptions.ServicioInvalidoException;
import com.barbershop.api.service.exceptions.TurnoConflictoException;
import com.barbershop.api.service.exceptions.UsuarioNoEncontradoException;

import lombok.AllArgsConstructor;

/**
 * Case de uso para asignar un turno a un cliente con un barbero específico.
 */
@Component
@AllArgsConstructor
public class AsignarTurnoInteractor {
    //TODO: REVISAR EL REPOSITORIO DE BARBEROS, no esta funcionando bien
    final BarberoRepository barberoRepository;
    final UsuarioRepository usuarioRepository;
    final ServicioRepository servicioRepository;
    final TurnoRepository turnoRepository;
    /* 
    @Caching(evict = {
        @CacheEvict(value = "turnosHoy", key = "'hoy'")
    })*/
    public Turno ejecutar(final String clienteId, final String barberoId, final String servicioId, final LocalDateTime fechaHora){
        var client = usuarioRepository.findByDni(clienteId);
        //var barber = barberoRepository.findByDni(barberoId);
        var barber = usuarioRepository.findByDni(barberoId);
        var service = servicioRepository.findById(servicioId);
        if (!client.isPresent()){
            throw new UsuarioNoEncontradoException("Cliente no encontrado con ID: " + clienteId);
        }

        if (!barber.isPresent()){
            throw new BarberoNoEncontradoException("Barbero no encontrado con ID: " + barberoId);
        }

        if (!service.isPresent()){
            throw new ServicioInvalidoException("Servicio no encontrado con ID: " + servicioId);
        }


        if (!service.get().getBarberosIds().contains(barberoId)) {
            throw new TurnoConflictoException("El barbero no ofrece el servicio");
        }
        var serviceDuration = service.get().getDuracionMinutos();
        LocalDateTime horaAtencionNueva = fechaHora;
        LocalDateTime horaFinalizacionNueva = fechaHora.plusMinutes(serviceDuration);
        var available = turnoRepository.findByBarberoIdAndHoraAtencionLessThanAndHoraFinalizacionGreaterThan(barberoId, horaFinalizacionNueva, horaAtencionNueva).stream().filter(t -> t.getEstado() != TurnoEstado.CANCELADO ||  t.getEstado() != TurnoEstado.COMPLETADO ).toList().isEmpty();
        if (!available) {
            throw new TurnoConflictoException("El barbero no está disponible en el horario solicitado");
        }

        var turno = new Turno();
        turno.setClienteId(clienteId);
        turno.setBarberoId(barberoId);
        turno.setServicioId(servicioId);
        turno.setHoraAtencion(horaAtencionNueva);
        turno.setHoraFinalizacion(horaFinalizacionNueva);
        turno.setEstado(TurnoEstado.PENDIENTE);
       return turnoRepository.save(turno);
    }

}
