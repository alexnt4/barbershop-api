package com.barbershop.api.transport.http.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.barbershop.api.domain.entities.Servicio;
import com.barbershop.api.service.dtos.ServicioRegisterDTO;
import com.barbershop.api.service.interactors.ServicioInteractor;

import java.util.List;

@RestController
@RequestMapping("/api/v1/servicios")
@CrossOrigin(origins = "*")
public class ServicioController {

    private final ServicioInteractor servicioInteractor;

    public ServicioController(ServicioInteractor servicioInteractor) {
        this.servicioInteractor = servicioInteractor;
    }

    @PostMapping("/registro")
    public ResponseEntity<Servicio> registrarServicio(@RequestBody ServicioRegisterDTO servicioDTO) {
        try {
            Servicio servicioRegistrado = servicioInteractor.registrarServicio(servicioDTO);
            return ResponseEntity.ok(servicioRegistrado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Servicio>> listarServicios() {
        List<Servicio> servicios = servicioInteractor.obtenerTodosLosServicios();
        return ResponseEntity.ok(servicios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Servicio> obtenerServicio(@PathVariable String id) {
        try {
            Servicio servicio = servicioInteractor.obtenerServicioPorId(id);
            return ResponseEntity.ok(servicio);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}