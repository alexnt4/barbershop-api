package com.barbershop.api.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "proveedores")
public class Proveedor {

    @Id
    private String id;

    private String nombre;
    private String contacto;
    private String telefono;
    private String email;
    private String condicionesEntrega;
    private String metodoPago;
    private BigDecimal precioCompra;

    // Relación con el producto asociado
    private String productoId;
}

