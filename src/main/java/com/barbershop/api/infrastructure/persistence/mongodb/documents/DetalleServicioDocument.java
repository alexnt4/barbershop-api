package com.barbershop.api.infrastructure.persistence.mongodb.documents;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetalleServicioDocument {
    private String servicioId;
    private String servicioNombre;
    private double precio;
    private int duracion;
}
