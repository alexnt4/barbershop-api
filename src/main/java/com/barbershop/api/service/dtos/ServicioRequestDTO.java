package com.barbershop.api.service.dtos;

import com.barbershop.api.domain.entities.Servicio;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import java.util.List;

@Data
public class ServicioRequestDTO {

    @NotBlank(message = "El nombre del servicio es obligatorio")
    private String nombre;

    @NotBlank(message = "La descripción del servicio es obligatoria")
    private String descripcion;

    @Positive(message = "La duración debe ser mayor a 0 minutos")
    private int duracionMinutos;

    @Positive(message = "El precio debe ser mayor que 0")
    private double precio;

    @NotEmpty(message = "Debe asignar al menos un barbero")
    private List<String> barberosIds;

    public Servicio toDomain() {
        return new Servicio(null, nombre, descripcion, duracionMinutos, precio, barberosIds);
    }
}
