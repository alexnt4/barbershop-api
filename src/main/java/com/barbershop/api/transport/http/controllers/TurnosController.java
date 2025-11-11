package com.barbershop.api.transport.http.controllers;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import com.barbershop.api.domain.value_objects.TurnoEstado;
import com.barbershop.api.service.dtos.TurnoDTO;
import com.barbershop.api.service.interactors.AsignarTurnoInteractor;
import com.barbershop.api.service.interactors.CancelarTurnoInteractor;
import com.barbershop.api.service.interactors.ConfirmarTurnoInteractor;
import com.barbershop.api.service.interactors.ObtenerTurnosInteractor;
import com.barbershop.api.service.interactors.ObtenerTurnosPorDiaInteractor;
import com.barbershop.api.service.interactors.TurnosPorBarberoInteractor;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

/**
 * Controller para la gestión de servicios de la barbería.
 */
@RestController
@RequestMapping("/api/v1/turnos")
@SecurityRequirement(name = "bearerAuth")
@AllArgsConstructor
@Tag(name = "Turnos", description = "Endpoints para la gestión de los turnos en la barbería")
public class TurnosController {
    final AsignarTurnoInteractor asignarTurnoInteractor;
    final ObtenerTurnosPorDiaInteractor obtenerTurnosPorDiaInteractor;
    final CancelarTurnoInteractor cancelarTurnoInteractor;
    final ConfirmarTurnoInteractor confirmarTurnoInteractor;
    final ObtenerTurnosInteractor obtenerTurnosInteractor;
    final TurnosPorBarberoInteractor turnosPorBarberoInteractor;

    @PostMapping("/asignar")
    @Operation(
        summary = "Asignar un nuevo turno",
        description = "Asigna un nuevo turno a un cliente con un barbero y servicio específicos"
    )
    public ResponseEntity<TurnoDTO> asignarTurno(@Valid @RequestBody TurnoDTO request) {
        var turno = asignarTurnoInteractor.ejecutar(request.getClienteId(), request.getBarberoId(), request.getServicioId(), request.getHoraAtencion());
        return ResponseEntity.status(201).body(TurnoDTO.fromDomain(turno));
    }

    @GetMapping
    @Operation(
        summary = "Obtener todos los turnos",
        description = "Obtiene todos los turnos, con opción de filtrar por estado"
    )
    public ResponseEntity<List<TurnoDTO>> obtenerTurnosPorBarbero(@PathVariable String barberoId, @RequestParam(required = false) TurnoEstado estado) {
        var turnos = turnosPorBarberoInteractor.ejecutar(barberoId, estado);
        return ResponseEntity.status(200).body(turnos.stream().map(TurnoDTO::fromDomain).toList());
    }



    @GetMapping("/{barberoId}")
    @Operation(
        summary = "Obtener turnos por barbero",
        description = "Obtiene los turnos asignados a un barbero específico, con opción de filtrar por estado"
    )

    public ResponseEntity<List<TurnoDTO>> obtenerTurnos(@PathVariable final String barberoId, @RequestParam(required = false) TurnoEstado estado) {
        var turnos = obtenerTurnosInteractor.ejecutar(estado);
        return ResponseEntity.status(200).body(turnos.stream().map(TurnoDTO::fromDomain).toList());
    }

    @GetMapping("/por-dia")
    @Operation(
        summary = "Obtener turnos por día",
        description = "Obtiene los turnos para un día específico, con opción de filtrar por estado"
    )
    public ResponseEntity<List<TurnoDTO>> obtenerTurnos(@RequestParam(required = true) LocalDateTime fechaInicio, @RequestParam(required = false) LocalDateTime fechaFin, @RequestParam(required = false, defaultValue = "PENDIENTE") TurnoEstado estado) {
        var turnos = obtenerTurnosPorDiaInteractor.ejecutar(fechaInicio, fechaFin, estado);
        return ResponseEntity.status(200).body(turnos.stream().map(TurnoDTO::fromDomain).toList());
    }

    @GetMapping("/hoy")
    @Operation(
        summary = "Obtener turnos del día actual",
        description = "Obtiene los turnos programados para el día actual"
    )
    public ResponseEntity<List<TurnoDTO>> obtenerTurnosHoy() {
        var turnos = obtenerTurnosPorDiaInteractor.ejecutar();
        return ResponseEntity.status(200).body(turnos.stream().map(TurnoDTO::fromDomain).toList());
    }

    @PutMapping("/confirmar/{turnoId}")
    @Operation(
        summary = "Confirmar un turno",
        description = "Confirma un turno específico, cambiando su estado a CONFIRMADO"
    )
    public ResponseEntity<Void> confirmarTurno(@PathVariable final String turnoId) {
        confirmarTurnoInteractor.ejecutar(turnoId);
        return ResponseEntity.status(204).build();
    }

    @DeleteMapping("/cancelar/{turnoId}")
    @Operation(
        summary = "Cancelar un turno",
        description = "Cancela un turno específico, cambiando su estado a CANCELADO"
    )
    public ResponseEntity<Void> cancelarTurno(@PathVariable final String turnoId) {
        cancelarTurnoInteractor.ejecutar(turnoId);
        return ResponseEntity.status(204).build();
    }

    @PutMapping("/finalizar/{turnoId}")
    @Operation(
        summary = "Finalizar un turno",
        description = "Finaliza un turno específico, cambiando su estado a COMPLETADO"
    )
    public ResponseEntity<Void> finalizarTurno(@PathVariable final String turnoId) {
        // TOOD: Por implementar FinalizarTurnoInteractor 
        return ResponseEntity.status(204).build();
    }



}
