package com.barbershop.api.service.interactors;

import com.barbershop.api.domain.entities.Producto;
import com.barbershop.api.domain.repositories.ProductoRepository;
import com.barbershop.api.service.dtos.MovimientoInventarioDTO;
import com.barbershop.api.service.dtos.ProductoResponseDTO;
import com.barbershop.api.service.exceptions.ProductoNoEncontradoException;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

/**
 * Interactor para registrar movimientos de inventario (entrada/salida).
 */
@Service
public class RegistrarMovimientoInventarioInteractor {
    /**
     * Repositorio de productos.
     */
    private final ProductoRepository productoRepository;

    /**
     * Constructor con inyección de dependencias.
     *
     * @param productoRepositoryParam Repositorio de productos.
     */
    public RegistrarMovimientoInventarioInteractor(
            final ProductoRepository productoRepositoryParam) {
        this.productoRepository = productoRepositoryParam;
    }

    /**
     * Registra un movimiento de inventario (entrada o salida).
     * Invalida el caché de productos y reportes.
     *
     * @param dto DTO con los datos del movimiento.
     * @return DTO de respuesta con el producto actualizado.
     * @throws ProductoNoEncontradoException si no se encuentra el producto.
     * @throws IllegalArgumentException      si el tipo de movimiento es inválido.
     */
    @CacheEvict(value = { "productos", "reporteInventario" }, allEntries = true)
    public ProductoResponseDTO execute(final MovimientoInventarioDTO dto) {
        // Buscar el producto
        Producto producto = productoRepository
                .findById(dto.getProductoId())
                .orElseThrow(() -> new ProductoNoEncontradoException(
                        "Producto no encontrado con ID: "
                                + dto.getProductoId()));

        // Registrar el movimiento según el tipo
        if ("ENTRADA".equalsIgnoreCase(dto.getTipoMovimiento())) {
            producto.registrarEntrada(dto.getCantidad());
        } else if ("SALIDA".equalsIgnoreCase(dto.getTipoMovimiento())) {
            producto.registrarSalida(dto.getCantidad());
        } else {
            throw new IllegalArgumentException(
                    "Tipo de movimiento inválido: "
                            + dto.getTipoMovimiento()
                            + ". Debe ser 'ENTRADA' o 'SALIDA'");
        }

        // Guardar los cambios
        Producto productoActualizado = productoRepository.save(producto);

        // Retornar DTO de respuesta
        return new ProductoResponseDTO(
                productoActualizado.getId(),
                productoActualizado.getNombre(),
                productoActualizado.getProveedor(),
                productoActualizado.getCantidadDisponible(),
                productoActualizado.getPrecioUnitario(),
                productoActualizado.calcularValorInventario(),
                productoActualizado.getFechaActualizacion(),
                productoActualizado.getProveedorId()
        )   ;
    }
}
