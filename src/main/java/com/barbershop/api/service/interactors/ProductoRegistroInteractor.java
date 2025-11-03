package com.barbershop.api.service.interactors;

import com.barbershop.api.domain.entities.Producto;
import com.barbershop.api.domain.repositories.ProductoRepository;
import com.barbershop.api.service.dtos.ProductoRegisterDTO;
import com.barbershop.api.service.dtos.ProductoResponseDTO;
import com.barbershop.api.service.exceptions.ProductoYaExisteException;
import org.springframework.stereotype.Service;

/**
 * Interactor para registrar un nuevo producto en el sistema.
 */
@Service
public class ProductoRegistroInteractor {
    /**
     * Repositorio de productos.
     */
    private final ProductoRepository productoRepository;

    /**
     * Constructor con inyección de dependencias.
     *
     * @param productoRepositoryParam Repositorio de productos.
     */
    public ProductoRegistroInteractor(
            final ProductoRepository productoRepositoryParam) {
        this.productoRepository = productoRepositoryParam;
    }

    /**
     * Registra un nuevo producto en el sistema.
     *
     * @param dto DTO con los datos del producto.
     * @return DTO de respuesta con el producto registrado.
     * @throws ProductoYaExisteException si ya existe un producto
     *                                   con el mismo nombre.
     */
    public ProductoResponseDTO execute(final ProductoRegisterDTO dto) {
        // Validar que no exista un producto con el mismo nombre
        if (productoRepository.existsByNombre(dto.getNombre())) {
            throw new ProductoYaExisteException(
                    "Ya existe un producto con el nombre: "
                            + dto.getNombre());
        }

        // Crear la entidad de dominio
        Producto producto = new Producto(
                dto.getNombre(),
                dto.getProveedor(),
                dto.getCantidadDisponible(),
                dto.getPrecioUnitario());

        // Guardar en el repositorio
        Producto productoGuardado = productoRepository.save(producto);

        // Convertir a DTO de respuesta
        return new ProductoResponseDTO(
                productoGuardado.getId(),
                productoGuardado.getNombre(),
                productoGuardado.getProveedor(),
                productoGuardado.getCantidadDisponible(),
                productoGuardado.getPrecioUnitario(),
                productoGuardado.calcularValorInventario(),
                productoGuardado.getFechaActualizacion());
    }
}
