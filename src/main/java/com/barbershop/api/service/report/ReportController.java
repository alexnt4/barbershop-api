package com.barbershop.api.service.report;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.barbershop.api.service.dtos.ReporteDesempenoBarberoDTO;
import com.barbershop.api.service.dtos.ReporteDisponibilidadDTO;
import com.barbershop.api.service.dtos.ReporteIngresosDTO;
import com.barbershop.api.service.dtos.ReporteProductosDTO;
import com.barbershop.api.service.dtos.ReporteServiciosMasSolicitadosDTO;
import com.barbershop.api.service.dtos.ReporteVentasDTO;
import com.barbershop.api.service.dtos.UsuarioResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/reportes")
@AllArgsConstructor
@Tag(name="Reportes y Metricas", description="Endpoints para obtener reportes de inventario, ventas, ingresos, barberos y disponibilidad")

public class ReportController {

    private final ReporteInteractor reportInteractor;

    @GetMapping("/inventario")
    @Operation(summary="Reporte de Inventario", description="Obtiene el reporte completo del inventario de productos")
    public ResponseEntity<List<ReporteProductosDTO>> inventario() {
        final List<ReporteProductosDTO> reporteProductos = reportInteractor.reporteInventario();
        return ResponseEntity.ok(reporteProductos);
    }

    @GetMapping("/ventas")
    @Operation(summary = "Reporte de Ventas", description = "Lista transacciones en un rango de fechas y clasificación por tipo")
    public ResponseEntity<ReporteVentasDTO> ventas(
    @RequestParam String inicio,
    @RequestParam String fin
    ) {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    LocalDateTime inicioparsed = Instant.parse(inicio).atZone(ZoneId.systemDefault()).toLocalDateTime();
    LocalDateTime finparsed = Instant.parse(fin).atZone(ZoneId.systemDefault()).toLocalDateTime();

    return ResponseEntity.ok(reportInteractor.reporteVentas(inicioparsed, finparsed));
    }
    
    @GetMapping("/desempeno-barberos")
    @Operation(summary = "Desempeño de Barberos", description = "Cantidad de servicios realizados por barbero en un periodo")
    public ResponseEntity<ReporteDesempenoBarberoDTO> desempeno(
    @RequestParam String inicio,
    @RequestParam String fin
    ) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    LocalDateTime inicioparsed = Instant.parse(inicio).atZone(ZoneId.systemDefault()).toLocalDateTime();
    LocalDateTime finparsed = Instant.parse(fin).atZone(ZoneId.systemDefault()).toLocalDateTime();
    return ResponseEntity.ok(reportInteractor.reporteDesempenoBarberos(inicioparsed, finparsed));
    }

    @GetMapping("/servicios-mas-solicitados")
    @Operation(summary = "Reporte de Servicios Más Solicitados", description = "Servicios más solicitados en función de las citas agendadas")
    public ResponseEntity<ReporteServiciosMasSolicitadosDTO> serviciosMasSolicitados(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fin
    ) {
        ReporteServiciosMasSolicitadosDTO reporte = reportInteractor.reporteServiciosMasSolicitados(inicio, fin);
        return ResponseEntity.ok(reporte);
    }

    @GetMapping("/ingresos")
@Operation(summary="Reporte de Ingresos Totales", description="Obtiene el reporte de ingresos totales en el rango de fechas especificado")
public ResponseEntity<ReporteIngresosDTO> ingresos(
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fin
) {
    ReporteIngresosDTO reporte = reportInteractor.reporteIngresosTotales(inicio, fin);
    return ResponseEntity.ok(reporte);
}
    @GetMapping("/disponibilidad")
    @Operation(summary="Reporte de Disponibilidad de Barberos", description="Disponibilidad horaria de los barberos en tiempo real")
    public ResponseEntity<ReporteDisponibilidadDTO> disponibilidad(
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime desde,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime hasta) {

        
        return ResponseEntity.ok(reportInteractor.reporteDisponibilidadd(desde, hasta));
    }
}
