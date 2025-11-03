package com.barbershop.api.service.interactors;

import com.barbershop.api.domain.entities.Producto;
import com.barbershop.api.domain.repositories.ProductoRepository;
import com.barbershop.api.service.dtos.ProductoResponseDTO;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Interactor para obtener productos con stock bajo.
 */
@Service
public class ObtenerProductosStockBajoInteractor {
    /**
     * Repositorio de productos.
     */
    private final ProductoRepository productoRepository;

    /**
     * Umbral predeterminado para considerar stock bajo.
     */
    private static final int UMBRAL_PREDETERMINADO = 10;

    /**
     * Constructor con inyección de dependencias.
     *
     * @param productoRepositoryParam Repositorio de productos.
     */
    public ObtenerProductosStockBajoInteractor(
            final ProductoRepository productoRepositoryParam) {
        this.productoRepository = productoRepositoryParam;
    }

    /**
     * Obtiene los productos con stock bajo usando umbral predeterminado.
     *
     * @return Lista de DTOs de productos con stock bajo.
     */
    public List<ProductoResponseDTO> execute() {
        return execute(UMBRAL_PREDETERMINADO);
    }

    /**
     * Obtiene los productos con stock bajo usando umbral especificado.
     *
     * @param umbral Umbral mínimo de stock.
     * @return Lista de DTOs de productos con stock bajo.
     */
    public List<ProductoResponseDTO> execute(final Integer umbral) {
        List<Producto> productos = productoRepository.findByStockBajo(umbral);

        return productos.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    /**
     * Convierte una entidad Producto a ProductoResponseDTO.
     *
     * @param producto Entidad del dominio.
     * @return DTO de respuesta.
     */
    private ProductoResponseDTO convertirADTO(final Producto producto) {
        return new ProductoResponseDTO(
                producto.getId(),
                producto.getNombre(),
                producto.getProveedor(),
                producto.getCantidadDisponible(),
                producto.getPrecioUnitario(),
                producto.calcularValorInventario(),
                producto.getFechaActualizacion());
    }
}
