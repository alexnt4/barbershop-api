package com.barbershop.api.service.interactors;

<<<<<<< HEAD
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
=======
import com.barbershop.api.domain.entities.Servicio;
import com.barbershop.api.domain.entities.Usuario;
import com.barbershop.api.domain.repositories.ServicioRepository;
import com.barbershop.api.domain.repositories.UsuarioRepository;
import com.barbershop.api.service.dtos.ServicioRequestDTO;
import com.barbershop.api.service.dtos.ServicioResponseDTO;
import com.barbershop.api.service.exceptions.BarberoNoEncontradoException;
import com.barbershop.api.service.exceptions.ServicioInvalidoException;
import com.barbershop.api.service.exceptions.UsuarioNoEncontradoException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ServicioInteractor {

    private final ServicioRepository servicioRepository;
    private final UsuarioRepository usuarioRepository;

    public ServicioInteractor(final ServicioRepository servicioRepository,
                              final UsuarioRepository usuarioRepository) {
        this.servicioRepository = servicioRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<ServicioResponseDTO> obtenerTodos() {
        return servicioRepository.findAll().stream()
                .map(ServicioResponseDTO::fromDomain)
                .collect(Collectors.toList());
    }

    public ServicioResponseDTO obtenerPorId(final String id) {
        Servicio servicio = servicioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNoEncontradoException("Servicio no encontrado con ID: " + id));
        return ServicioResponseDTO.fromDomain(servicio);
    }

    public ServicioResponseDTO crear(final ServicioRequestDTO request) {
        validarBarberos(request.getBarberosIds());
        validarUnicidad(null, request.getNombre(), request.getDescripcion());
        Servicio guardado = servicioRepository.save(request.toDomain());
        return ServicioResponseDTO.fromDomain(guardado);
    }

    public ServicioResponseDTO actualizar(final String id, final ServicioRequestDTO request) {
        Servicio servicioExistente = servicioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNoEncontradoException("No se encontró el servicio con ID: " + id));

        validarBarberos(request.getBarberosIds());
        validarUnicidad(id, request.getNombre(), request.getDescripcion());

        servicioExistente.setNombre(request.getNombre());
        servicioExistente.setDescripcion(request.getDescripcion());
        servicioExistente.setDuracionMinutos(request.getDuracionMinutos());
        servicioExistente.setPrecio(request.getPrecio());
        servicioExistente.setBarberosIds(request.getBarberosIds());

        Servicio actualizado = servicioRepository.save(servicioExistente);
        return ServicioResponseDTO.fromDomain(actualizado);
    }

    public void eliminar(final String id) {
        servicioRepository.deleteById(id);
    }

    private void validarBarberos(final List<String> barberosIds) {
        for (String barberoId : barberosIds) {
            Usuario usuario = usuarioRepository.findByDni(barberoId)
                    .orElseThrow(() -> new BarberoNoEncontradoException("No existe barbero con ID: " + barberoId));

            if (!"BARBERO".equals(usuario.getRole().name())) {
                throw new BarberoNoEncontradoException("El usuario con ID " + barberoId + " no tiene rol de BARBERO.");
            }
        }
    }

    private void validarUnicidad(final String idActual, final String nombre, final String descripcion) {
        boolean existe = servicioRepository.findAll().stream()
                .anyMatch(s -> {
                    boolean mismoNombre = Objects.equals(s.getNombre().trim().toLowerCase(), nombre.trim().toLowerCase());
                    boolean mismaDescripcion = Objects.equals(s.getDescripcion().trim().toLowerCase(), descripcion.trim().toLowerCase());
                    if (idActual == null) {
                        return mismoNombre || mismaDescripcion;
                    } else {
                        return !Objects.equals(s.getId(), idActual) && (mismoNombre || mismaDescripcion);
                    }
                });
        if (existe) {
            throw new ServicioInvalidoException("Ya existe un servicio con el mismo nombre o descripción");
        }
    }
}
>>>>>>> feature/services
