package com.barbershop.api.infrastructure.persistence.mongodb.documents;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "servicios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServicioDocument {
    @Id
    private String id;
    private String nombre;
    private String descripcion;
    private double precioBase;
    private int duracionEstimada;
}
