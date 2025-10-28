package com.barbershop.api.transport.http.controllers;

import com.barbershop.api.service.dtos.BarberoResponseDTO;
import com.barbershop.api.service.interactors.ObtenerBarberosInteractor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller para gestión de barberos.
 */
@RestController
@RequestMapping("/api/v1/barberos")
@AllArgsConstructor
@Tag(name = "Barberos", description = "Endpoints para gestión de barberos")
public class BarberoController {

    private final ObtenerBarberosInteractor obtenerBarberosInteractor;

    /**
     * Obtiene la lista de todos los usuarios con rol BARBERO (público).
     *
     * @return lista de barberos
     */
    @GetMapping
    @Operation(summary = "Listar barberos", description = "Obtiene la lista de todos los usuarios con rol BARBERO")
    public ResponseEntity<List<BarberoResponseDTO>> listarBarberos() {
        final List<BarberoResponseDTO> barberos = obtenerBarberosInteractor.ejecutar();
        return ResponseEntity.ok(barberos);
    }
}
