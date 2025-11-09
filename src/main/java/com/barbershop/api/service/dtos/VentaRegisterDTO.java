package com.barbershop.api.service.dtos;

import lombok.Data;
import java.util.List;

@Data
public class VentaRegisterDTO {
    private String clienteId;
    private String barberoId;
    private List<DetalleProductoDTO> productos;
    private List<DetalleServicioDTO> servicios;
    private String metodoPago;
}

