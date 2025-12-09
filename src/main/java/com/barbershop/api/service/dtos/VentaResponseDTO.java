package com.barbershop.api.service.dtos;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;
import java.math.BigDecimal;

@Data
public class VentaResponseDTO {
    private String id;
    private LocalDateTime fecha;
    private String clienteId;
    private String clienteNombre;
    private String barberoId;
    private String barberoNombre;
    private List<DetalleProductoResponseDTO> productos;
    private List<DetalleServicioResponseDTO> servicios;
    private BigDecimal montoTotal; 
    private String metodoPago;
    private String estado;
}



