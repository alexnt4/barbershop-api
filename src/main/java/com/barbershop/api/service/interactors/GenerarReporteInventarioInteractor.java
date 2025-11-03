package com.barbershop.api.service.interactors;

import com.barbershop.api.domain.entities.Producto;
import com.barbershop.api.domain.repositories.ProductoRepository;
import com.barbershop.api.service.dtos.ReporteInventarioDTO;
import com.barbershop.api.service.dtos.ReporteInventarioDTO.ProductoReporteDTO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Interactor para generar el reporte completo de inventario.
 */
@Service
public class GenerarReporteInventarioInteractor {
    /**
     * Repositorio de productos.
     */
    private final ProductoRepository productoRepository;

    /**
     * Umbral por defecto para considerar stock bajo.
     */
    private static final Integer UMBRAL_STOCK_BAJO = 10;

    /**
     * Constructor con inyección de dependencias.
     *
     * @param productoRepositoryParam Repositorio de productos.
     */
    public GenerarReporteInventarioInteractor(
            final ProductoRepository productoRepositoryParam) {
        this.productoRepository = productoRepositoryParam;
    }

    /**
     * Genera el reporte completo de inventario.
     *
     * @return DTO con el reporte de inventario.
     */
    public ReporteInventarioDTO execute() {
        // Obtener todos los productos
        List<Producto> productos = productoRepository.findAll();

        // Convertir a DTOs de reporte
        List<ProductoReporteDTO> productosReporte = productos.stream()
                .map(this::convertirAProductoReporte)
                .collect(Collectors.toList());

        // Calcular estadísticas globales
        Integer totalProductos = productos.size();
        Integer totalUnidades = productos.stream()
                .mapToInt(Producto::getCantidadDisponible)
                .sum();
        BigDecimal valorTotal = productos.stream()
                .map(Producto::calcularValorInventario)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        Integer productosStockBajo = (int) productos.stream()
                .filter(p -> p.tieneStockBajo(UMBRAL_STOCK_BAJO))
                .count();

        return new ReporteInventarioDTO(
                productosReporte,
                totalProductos,
                totalUnidades,
                valorTotal,
                productosStockBajo);
    }

    /**
     * Convierte un producto a su DTO de reporte.
     *
     * @param producto Producto a convertir.
     * @return DTO de reporte del producto.
     */
    private ProductoReporteDTO convertirAProductoReporte(final Producto producto) {
        return new ProductoReporteDTO(
                producto.getId(),
                producto.getNombre(),
                producto.getProveedor(),
                producto.getCantidadDisponible(),
                producto.getPrecioUnitario(),
                producto.calcularValorInventario(),
                producto.tieneStockBajo(UMBRAL_STOCK_BAJO));
    }
}
