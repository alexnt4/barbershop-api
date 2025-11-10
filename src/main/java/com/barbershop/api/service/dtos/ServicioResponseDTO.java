package com.barbershop.api.service.dtos;

import java.util.List;

import com.barbershop.api.domain.entities.Servicio;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * DTO para devolver información de los servicios al cliente.
 */
@Data
@AllArgsConstructor
public class ServicioResponseDTO {
    private String id;
    private String nombre;
    private String descripcion;
    private int duracionMinutos;
    private double precio;
    private List<String> barberosIds;

    public static ServicioResponseDTO fromDomain(Servicio servicio) {
        return new ServicioResponseDTO(
                servicio.getId(),
                servicio.getNombre(),
                servicio.getDescripcion(),
                servicio.getDuracionMinutos(),
                servicio.getPrecio(),
                servicio.getBarberosIds()
        );
    }
}
