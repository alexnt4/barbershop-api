package com.barbershop.api.service.interactors;

import org.springframework.stereotype.Service; // ¡Agregar esta import!
import com.barbershop.api.domain.entities.Servicio;
import com.barbershop.api.domain.repositories.ServicioRepository;
import com.barbershop.api.service.dtos.ServicioRegisterDTO;

import java.util.List;

@Service 
public class ServicioInteractor {
    
    private final ServicioRepository servicioRepository;
    
    public ServicioInteractor(ServicioRepository servicioRepository) {
        this.servicioRepository = servicioRepository;
    }
    
    public Servicio registrarServicio(ServicioRegisterDTO servicioDTO) {
        Servicio servicio = new Servicio(
            null, // ID se generará automáticamente
            servicioDTO.getNombre(),
            servicioDTO.getDescripcion(),
            servicioDTO.getPrecioBase(),
            servicioDTO.getDuracionEstimada()
        );
        
        return servicioRepository.save(servicio);
    }
    
    public List<Servicio> obtenerTodosLosServicios() {
        return servicioRepository.findAll();
    }
    
    public Servicio obtenerServicioPorId(String id) {
        return servicioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Servicio no encontrado: " + id));
    }
}