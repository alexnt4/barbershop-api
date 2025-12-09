package com.barbershop.api.transport.http.controllers;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.barbershop.api.service.dtos.VentaRegisterDTO;
import com.barbershop.api.service.dtos.VentaResponseDTO;
import com.barbershop.api.service.dtos.ReporteVentasDTO;
import com.barbershop.api.service.interactors.VentaRegistroInteractor;
import com.barbershop.api.service.interactors.VentaEliminarInteractor;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.barbershop.api.service.interactors.ObtenerVentasInteractor;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/ventas")
@CrossOrigin(origins = "*")
@Tag(name = "Ventas", description = "Endpoints para gestión de ventas")
public class VentaController {

    private final VentaRegistroInteractor ventaRegistroInteractor;
    private final ObtenerVentasInteractor obtenerVentasInteractor;
    private final VentaEliminarInteractor ventaEliminarInteractor;

    public VentaController(VentaRegistroInteractor ventaRegistroInteractor,
                          ObtenerVentasInteractor obtenerVentasInteractor,
                          VentaEliminarInteractor ventaEliminarInteractor) {
        this.ventaRegistroInteractor = ventaRegistroInteractor;
        this.obtenerVentasInteractor = obtenerVentasInteractor;
        this.ventaEliminarInteractor = ventaEliminarInteractor;
    }

    @Operation(summary = "Registrar nueva venta", description = "Crea una nueva venta de productos y/o servicios")
    @PostMapping
    public ResponseEntity<VentaResponseDTO> registrarVenta(@RequestBody VentaRegisterDTO ventaDTO) {
        try {
            var ventaRegistrada = ventaRegistroInteractor.registrarVenta(ventaDTO);
            return ResponseEntity.ok(ventaRegistrada);
        } catch (Exception e) {
            System.out.println("ERROR en Controller: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Obtener todas las ventas", description = "Retorna el listado completo de ventas")
    @GetMapping
    public ResponseEntity<List<VentaResponseDTO>> obtenerTodasLasVentas() {
        var ventas = obtenerVentasInteractor.obtenerTodasLasVentas();
        return ResponseEntity.ok(ventas);
    }

    @Operation(summary = "Obtener ventas por cliente", description = "Busca ventas por ID del cliente")
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<VentaResponseDTO>> obtenerVentasPorCliente(@PathVariable String clienteId) {
        var ventas = obtenerVentasInteractor.obtenerVentasPorCliente(clienteId);
        return ResponseEntity.ok(ventas);
    }

    @Operation(summary = "Obtener ventas por barbero", description = "Busca ventas por ID del barbero")
    @GetMapping("/barbero/{barberoId}")
    public ResponseEntity<List<VentaResponseDTO>> obtenerVentasPorBarbero(@PathVariable String barberoId) {
        var ventas = obtenerVentasInteractor.obtenerVentasPorBarbero(barberoId);
        return ResponseEntity.ok(ventas);
    }

    @Operation(summary = "Generar reporte de ventas", description = "Genera reporte analítico de ventas por rango de fechas")
    @GetMapping("/reportes")
    public ResponseEntity<ReporteVentasDTO> generarReporte(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fin) {
        var reporte = obtenerVentasInteractor.generarReporteVentas(inicio, fin);
        return ResponseEntity.ok(reporte);
    }

    @Operation(summary = "Eliminar venta", description = "Elimina una venta por su ID")
    @DeleteMapping("/{ventaId}")
    public ResponseEntity<Void> eliminarVenta(@PathVariable String ventaId) {
        try {
            // Necesitarás inyectar VentaEliminarInteractor en el controller
            ventaEliminarInteractor.ejecutar(ventaId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            System.out.println("ERROR eliminando venta: " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}