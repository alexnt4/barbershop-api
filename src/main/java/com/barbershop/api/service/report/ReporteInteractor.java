package com.barbershop.api.service.report;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import com.barbershop.api.domain.entities.*;
import com.barbershop.api.domain.entities.especializaciones.DetalleServicio;
import com.barbershop.api.domain.repositories.*;

import com.barbershop.api.domain.value_objects.TurnoEstado;
import com.barbershop.api.service.dtos.*;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ReporteInteractor {

    private final ProductoRepository productoRepository;
    private final VentaRepository ventaRepository;
    private final TurnoRepository turnoRepository;
    private final UsuarioRepository usuarioRepository;

    // ================== INVENTARIO ==================
    public List<ReporteProductosDTO> reporteInventario() {
        return productoRepository.findAll().stream().map(producto -> {
            ReporteProductosDTO reporte = new ReporteProductosDTO();
            reporte.setId(producto.getId());
            reporte.setNombre(producto.getNombre());
            reporte.setPrecio(producto.getPrecioUnitario());
            reporte.setProveedor(producto.getProveedor());
            reporte.setStock(producto.getCantidadDisponible());
            return reporte;
        }).collect(Collectors.toList());
    }

    // ================== VENTAS ==================
    public ReporteVentasDTO reporteVentas(LocalDateTime inicio, LocalDateTime fin) {
        List<Venta> ventas_rango = ventaRepository.findByFechaBetween(inicio, fin);

        ReporteVentasDTO reporte = new ReporteVentasDTO();
        reporte.setFechaInicio(inicio);
        reporte.setFechaFin(fin);

        double totalVentas = ventas_rango.stream()
                .map(Venta::getMontoTotal)
                .mapToDouble(BigDecimal::doubleValue)
                .sum();
        reporte.setTotalTransacciones(ventas_rango.size());
        reporte.setTotalVentas(totalVentas);

        reporte.setVentasPorMetodoPago(
                ventas_rango.stream()
                        .collect(Collectors.groupingBy(
                                Venta::getMetodoPago,
                                Collectors.mapping(
                                        Venta::getMontoTotal,
                                        Collectors.reducing(0.0, BigDecimal::doubleValue, Double::sum)
                                )
                        ))
        );

        reporte.setVentasPorBarbero(
                ventas_rango.stream()
                        .collect(Collectors.groupingBy(
                                venta -> venta.getBarbero().getNombre(),
                                Collectors.reducing(0.0, venta -> venta.getMontoTotal().doubleValue(), Double::sum)
                        ))
        );

        return reporte;
    }

    // ================== DESEMPEÑO BARBEROS ==================
    public ReporteDesempenoBarberoDTO reporteDesempenoBarberos(LocalDateTime inicio, LocalDateTime fin) {
        List<Turno> turnosRango = turnoRepository.findByHoraAtencionBetween(inicio, fin);

        Map<String, Long> countPorBarberoId = turnosRango.stream()
                .filter(turno -> turno.getEstado() == TurnoEstado.COMPLETADO)
                .collect(Collectors.groupingBy(Turno::getBarberoId, Collectors.counting()));

        Map<String, Integer> serviciosPorBarbero = new HashMap<>();
        for (Map.Entry<String, Long> entry : countPorBarberoId.entrySet()) {
            String barberoId = entry.getKey();
            Long cantidad = entry.getValue();

            Usuario barbero = usuarioRepository.findByDni(barberoId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No se ha encontrado al barbero."));
            String nombre = barbero != null ? barbero.getNombre() : "Desconocido";

            serviciosPorBarbero.put(nombre, cantidad.intValue());
        }

        ReporteDesempenoBarberoDTO reporte = new ReporteDesempenoBarberoDTO();
        reporte.setFechaInicio(inicio);
        reporte.setFechaFin(fin);
        reporte.setServiciosPorBarbero(serviciosPorBarbero);

        return reporte;
    }

    // ================== SERVICIOS MÁS SOLICITADOS ==================
    public ReporteServiciosMasSolicitadosDTO reporteServiciosMasSolicitados(LocalDateTime inicio, LocalDateTime fin) {
        List<Venta> ventasRango = ventaRepository.findByFechaBetween(inicio, fin);

        Map<String, Long> serviciosCount = ventasRango.stream()
                .flatMap(venta -> venta.getServicios().stream())
                .map(detalle -> detalle.getServicio().getNombre())
                .collect(Collectors.groupingBy(nombre -> nombre, Collectors.counting()));

        Map<String, Integer> serviciosSolicitados = serviciosCount.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().intValue()));

        ReporteServiciosMasSolicitadosDTO reporte = new ReporteServiciosMasSolicitadosDTO();
        reporte.setFechaInicio(inicio);
        reporte.setFechaFin(fin);
        reporte.setServiciosSolicitados(serviciosSolicitados);

        return reporte;
    }

    // ================== INGRESOS ==================
    public ReporteIngresosDTO reporteIngresosTotales(LocalDateTime inicio, LocalDateTime fin) {
        List<Venta> ventasRango = ventaRepository.findByFechaBetween(inicio, fin);

        BigDecimal totalIngresos = ventasRango.stream()
                .map(Venta::getMontoTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        ReporteIngresosDTO reporte = new ReporteIngresosDTO();
        reporte.setFechaInicio(inicio);
        reporte.setFechaFin(fin);
        reporte.setTotalIngresos(totalIngresos.doubleValue());

        return reporte;
    }

    public ReporteDisponibilidadDTO reporteDisponibilidadd(LocalDateTime desde, LocalDateTime hasta) {
        List<Turno> turnos = turnoRepository.findByHoraAtencionBetween(desde, hasta);

        Map<String, List<Turno>> turnosPorBarbero = turnos.stream()
                .collect(Collectors.groupingBy(Turno::getBarberoId));

        Map<String, List<ReporteDisponibilidadDTO.IntervaloDisponible>> disponibilidadPorBarbero = new HashMap<>();

        for (Map.Entry<String, List<Turno>> entry : turnosPorBarbero.entrySet()) {
            String barberoId = entry.getKey();
            List<Turno> turnosBarbero = entry.getValue();
            turnosBarbero.sort(Comparator.comparing(Turno::getHoraAtencion));

            List<ReporteDisponibilidadDTO.IntervaloDisponible> intervalosDisponibles = new ArrayList<>();
            LocalDateTime tiempoActual = desde;

            for (Turno turno : turnosBarbero) {
                if (tiempoActual.isBefore(turno.getHoraAtencion())) {
                    intervalosDisponibles.add(
                            new ReporteDisponibilidadDTO.IntervaloDisponible(tiempoActual, turno.getHoraAtencion()));
                }
                tiempoActual = turno.getHoraFinalizacion().isAfter(tiempoActual)
                        ? turno.getHoraFinalizacion()
                        : tiempoActual;
            }

            if (tiempoActual.isBefore(hasta)) {
                intervalosDisponibles.add(new ReporteDisponibilidadDTO.IntervaloDisponible(tiempoActual, hasta));
            }

            Usuario barbero = usuarioRepository.findByDni(barberoId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No se ha encontrado al barbero.")); 
            String nombre = barbero != null ? barbero.getNombre() : "Desconocido";

            disponibilidadPorBarbero.put(nombre, intervalosDisponibles);
        }

        ReporteDisponibilidadDTO reporte = new ReporteDisponibilidadDTO();
        reporte.setFechaConsulta(LocalDateTime.now());
        reporte.setDisponibilidadPorBarbero(disponibilidadPorBarbero);

        return reporte;
    }
}
