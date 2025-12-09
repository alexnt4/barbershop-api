package com.barbershop.api.infrastructure.persistence.mongodb.documents;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "ventas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VentaDocument {
    @Id
    private String id;
    private LocalDateTime fecha;
    private String clienteId;
    private String clienteNombre;
    private String barberoId;
    private String barberoNombre;
    private List<DetalleProductoDocument> productos;
    private List<DetalleServicioDocument> servicios;
    private double montoTotal;
    private String metodoPago;
    private String estado;
}