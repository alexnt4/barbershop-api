package com.barbershop.api.domain.repositories;

import com.barbershop.api.domain.entities.Producto;
import java.util.List;
import java.util.Optional;

/**
 * Repositorio para la gestión de productos.
 * Define las operaciones de persistencia para los productos.
 */
public interface ProductoRepository {
    /**
     * Guarda un producto en la base de datos.
     *
     * @param producto Producto a guardar.
     * @return Producto guardado con su ID generado.
     */
    Producto save(Producto producto);

    /**
     * Busca un producto por su ID.
     *
     * @param id ID del producto.
     * @return Optional con el producto si existe.
     */
    Optional<Producto> findById(String id);

    /**
     * Busca un producto por su nombre.
     *
     * @param nombre Nombre del producto.
     * @return Optional con el producto si existe.
     */
    Optional<Producto> findByNombre(String nombre);

    /**
     * Obtiene todos los productos.
     *
     * @return Lista de todos los productos.
     */
    List<Producto> findAll();

    /**
     * Busca productos por proveedor.
     *
     * @param proveedor Nombre del proveedor.
     * @return Lista de productos del proveedor.
     */
    List<Producto> findByProveedor(String proveedor);

    /**
     * Busca productos con stock bajo.
     *
     * @param umbral Umbral mínimo de stock.
     * @return Lista de productos con stock bajo.
     */
    List<Producto> findByStockBajo(Integer umbral);

    /**
     * Elimina un producto por su ID.
     *
     * @param id ID del producto a eliminar.
     */
    void deleteById(String id);

    /**
     * Verifica si existe un producto con el nombre especificado.
     *
     * @param nombre Nombre del producto.
     * @return true si existe, false en caso contrario.
     */
    boolean existsByNombre(String nombre);
}
