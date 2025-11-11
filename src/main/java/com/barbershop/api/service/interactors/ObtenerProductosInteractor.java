package com.barbershop.api.service.interactors;

import com.barbershop.api.domain.entities.Producto;
import com.barbershop.api.domain.repositories.ProductoRepository;
import com.barbershop.api.service.dtos.ProductoResponseDTO;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Interactor para obtener todos los productos del inventario.
 */
@Service
public class ObtenerProductosInteractor {
    /**
     * Repositorio de productos.
     */
    private final ProductoRepository productoRepository;

    /**
     * Constructor con inyección de dependencias.
     *
     * @param productoRepositoryParam Repositorio de productos.
     */
    public ObtenerProductosInteractor(
            final ProductoRepository productoRepositoryParam) {
        this.productoRepository = productoRepositoryParam;
    }

    /**
     * Obtiene todos los productos del inventario.
     * Este método cachea el resultado en Redis por 5 minutos.
     *
     * @return Lista de DTOs de productos.
     */
    @Cacheable(value = "productos", key = "'lista-completa'")
    public List<ProductoResponseDTO> execute() {
        List<Producto> productos = productoRepository.findAll();

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
