package com.barbershop.api.service.interactors;

import com.barbershop.api.domain.entities.Producto;
import com.barbershop.api.domain.repositories.ProductoRepository;
import com.barbershop.api.service.dtos.ProductoResponseDTO;
import com.barbershop.api.service.exceptions.ProductoNoEncontradoException;
import org.springframework.stereotype.Service;

/**
 * Interactor para obtener un producto por su ID.
 */
@Service
public class ObtenerProductoPorIdInteractor {
    /**
     * Repositorio de productos.
     */
    private final ProductoRepository productoRepository;

    /**
     * Constructor con inyección de dependencias.
     *
     * @param productoRepositoryParam Repositorio de productos.
     */
    public ObtenerProductoPorIdInteractor(
            final ProductoRepository productoRepositoryParam) {
        this.productoRepository = productoRepositoryParam;
    }

    /**
     * Obtiene un producto por su ID.
     *
     * @param id ID del producto.
     * @return DTO de respuesta con el producto.
     * @throws ProductoNoEncontradoException si no se encuentra el producto.
     */
    public ProductoResponseDTO execute(final String id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ProductoNoEncontradoException(
                        "Producto no encontrado con ID: " + id));

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
