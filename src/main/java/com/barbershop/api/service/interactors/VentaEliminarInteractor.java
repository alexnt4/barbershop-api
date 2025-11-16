package com.barbershop.api.service.interactors;

import org.springframework.stereotype.Service;
import com.barbershop.api.domain.repositories.VentaRepository;

@Service
public class VentaEliminarInteractor {

    private final VentaRepository ventaRepository;

    public VentaEliminarInteractor(VentaRepository ventaRepository) {
        this.ventaRepository = ventaRepository;
    }

    public void ejecutar(String ventaId) {
       
        if (!ventaRepository.findById(ventaId).isPresent()) {
            throw new RuntimeException("Venta no encontrada con ID: " + ventaId);
        }
        ventaRepository.deleteById(ventaId);
    }
}