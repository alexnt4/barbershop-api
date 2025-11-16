package com.barbershop.api.service.interactors;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import com.barbershop.api.domain.entities.Turno;
import com.barbershop.api.domain.repositories.TurnoRepository;
import com.barbershop.api.domain.value_objects.TurnoEstado;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ObtenerTurnosPorDiaInteractor {

    private final TurnoRepository turnoRepository;

    public  List<Turno> ejecutar(final LocalDateTime inicio, final LocalDateTime fin, final TurnoEstado estado) {
        LocalDate hoy = LocalDate.now();
        LocalDateTime inicioDelDia = hoy.atStartOfDay(); 
        LocalDateTime finDelDia = hoy.atTime(LocalTime.MAX);
        TurnoEstado estadoFiltro = estado != null ? estado : TurnoEstado.PENDIENTE;
        if (inicio != null) {
            inicioDelDia = inicio;
            finDelDia = inicio.toLocalDate().atTime(LocalTime.MAX);
        }
        if (fin != null) {
            finDelDia = fin;
        }
        var filter = turnoRepository.findByHoraAtencionBetween(inicioDelDia, finDelDia);
        filter = filter.stream().filter(turno -> turno.getEstado() == estadoFiltro).toList();
        return filter;
    }

  //  @Cacheable(value = "turnosHoy", key = "'hoy'")
    public List<Turno> ejecutar() {
        LocalDate hoy = LocalDate.now();
        LocalDateTime inicioDelDia = hoy.atStartOfDay(); 
        LocalDateTime finDelDia = hoy.atTime(LocalTime.MAX);
        var filter = turnoRepository.findByHoraAtencionBetween(inicioDelDia, finDelDia);
        filter = filter.stream().filter(turno -> turno.getEstado() == TurnoEstado.PENDIENTE).toList();
        return filter;
    }
     
}
