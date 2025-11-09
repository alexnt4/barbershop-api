package com.barbershop.api.service.interactors;

import org.springframework.stereotype.Service;
import com.barbershop.api.domain.repositories.VentaRepository;
import com.barbershop.api.service.dtos.VentaResponseDTO;
import com.barbershop.api.service.dtos.ReporteVentasDTO;
import com.barbershop.api.service.dtos.DetalleProductoResponseDTO;
import com.barbershop.api.service.dtos.DetalleServicioResponseDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ObtenerVentasInteractor {

    private final VentaRepository ventaRepository;

    public ObtenerVentasInteractor(VentaRepository ventaRepository) {
        this.ventaRepository = ventaRepository;
    }

    public List<VentaResponseDTO> obtenerTodasLasVentas() {
        return ventaRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<VentaResponseDTO> obtenerVentasPorCliente(String clienteId) {
        return ventaRepository.findByClienteId(clienteId).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<VentaResponseDTO> obtenerVentasPorBarbero(String barberoId) {
    return ventaRepository.findByBarberoId(barberoId).stream()
            .map(this::toResponseDTO)
            .collect(Collectors.toList());
    }

    public List<VentaResponseDTO> obtenerVentasPorRangoFechas(LocalDateTime inicio, LocalDateTime fin) {
        return ventaRepository.findByFechaBetween(inicio, fin).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public ReporteVentasDTO generarReporteVentas(LocalDateTime inicio, LocalDateTime fin) {
        var ventas = ventaRepository.findByFechaBetween(inicio, fin);
        
        var reporte = new ReporteVentasDTO();
        reporte.setFechaInicio(inicio);
        reporte.setFechaFin(fin);
        reporte.setTotalVentas(ventas.stream()
                .map(v -> v.getMontoTotal())
                .reduce(java.math.BigDecimal.ZERO, java.math.BigDecimal::add)
                .doubleValue());
        reporte.setTotalTransacciones(ventas.size());
        
        
        return reporte;
    }

    private VentaResponseDTO toResponseDTO(com.barbershop.api.domain.entities.Venta venta) {
        var response = new VentaResponseDTO();
        response.setId(venta.getId());
        response.setFecha(venta.getFecha());
        response.setClienteId(venta.getCliente().getDni());
        response.setClienteNombre(venta.getCliente().getNombre());
        
        if (venta.getBarbero() != null) {
            response.setBarberoId(venta.getBarbero().getDni());
            response.setBarberoNombre(venta.getBarbero().getNombre());
        }
        
        if (venta.getProductos() != null) {
            response.setProductos(venta.getProductos().stream()
                    .map(dp -> {
                        var dto = new DetalleProductoResponseDTO();
                        dto.setProductoId(dp.getProducto().getId());
                        dto.setProductoNombre(dp.getProducto().getNombre());
                        dto.setCantidad(dp.getCantidad());
                        dto.setPrecioVenta(dp.getPrecioVenta());
                        dto.setSubtotal(dp.getSubtotal());
                        return dto;
                    })
                    .collect(Collectors.toList()));
        } else {
            response.setProductos(new ArrayList<>()); // Lista vacía en lugar de null
        }
        
        if (venta.getServicios() != null) {
            response.setServicios(venta.getServicios().stream()
                    .map(ds -> {
                        var dto = new DetalleServicioResponseDTO();
                        dto.setServicioId(ds.getServicio().getId());
                        dto.setServicioNombre(ds.getServicio().getNombre());
                        dto.setPrecio(ds.getPrecio());
                        dto.setDuracion(ds.getDuracion());
                        return dto;
                    })
                    .collect(Collectors.toList()));
        } else {
            response.setServicios(new ArrayList<>()); // Lista vacía en lugar de null
        }
        
        response.setMontoTotal(venta.getMontoTotal());
        response.setMetodoPago(venta.getMetodoPago());
        response.setEstado(venta.getEstado());
        
        return response;
    }
}