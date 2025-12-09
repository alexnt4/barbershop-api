package com.barbershop.api.service.dtos;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReporteProductosDTO {
    private String id;
    private String nombre;
    private Integer stock;
    private String proveedor;
    private BigDecimal precio;
}