package com.barbershop.api.transport.http.controllers;

import com.barbershop.api.service.dtos.ServicioRequestDTO;
import com.barbershop.api.service.dtos.ServicioResponseDTO;
import com.barbershop.api.service.interactors.ServicioInteractor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller para la gestión de servicios de la barbería.
 */
@RestController
@RequestMapping("/api/v1/servicios")
@AllArgsConstructor
@Tag(name = "Servicios", description = "Endpoints para la gestión de los servicios ofrecidos en la barbería")
public class ServicioController {

    private final ServicioInteractor servicioInteractor;

    @GetMapping
    @SecurityRequirement(name = "bearerAuth")
    @Operation(
        summary = "Listar servicios",
        description = "Obtiene la lista completa de servicios ofrecidos por la barbería"
    )
    public ResponseEntity<List<ServicioResponseDTO>> obtenerTodos() {
        return ResponseEntity.ok(servicioInteractor.obtenerTodos());
    }

    @GetMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'BARBERO', 'CLIENTE')")
    @Operation(
        summary = "Obtener servicio por ID",
        description = "Busca un servicio por su identificador único"
    )
    public ResponseEntity<ServicioResponseDTO> obtenerPorId(@PathVariable final String id) {
        return ResponseEntity.ok(servicioInteractor.obtenerPorId(id));
    }

    @PostMapping
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @Operation(
        summary = "Crear servicio",
        description = "Crea un nuevo servicio en la barbería (solo administradores)"
    )
    public ResponseEntity<ServicioResponseDTO> crear(@Valid @RequestBody final ServicioRequestDTO request) {
        final ServicioResponseDTO servicioCreado = servicioInteractor.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(servicioCreado);
    }

    @PutMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @Operation(
        summary = "Actualizar servicio",
        description = "Actualiza los datos de un servicio existente (solo administradores)"
    )
    public ResponseEntity<ServicioResponseDTO> actualizar(
            @PathVariable final String id,
            @Valid @RequestBody final ServicioRequestDTO request) {
        return ResponseEntity.ok(servicioInteractor.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @Operation(
        summary = "Eliminar servicio",
        description = "Elimina un servicio del sistema (solo administradores)"
    )
    public ResponseEntity<Void> eliminar(@PathVariable final String id) {
        servicioInteractor.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
