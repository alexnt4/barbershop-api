package com.barbershop.api.domain.repositories;

import java.util.List;
import java.util.Optional;

import com.barbershop.api.domain.entities.Venta;

import java.time.LocalDateTime;

/**
 * Representa el repositorio de ventas.
 */
public interface VentaRepository {
    /**
     * Método que permite guardar una venta en el repositorio.
     */
    Venta save(Venta venta);
    
    /**
     * Método que permite buscar una venta por su ID.
     */
    Optional<Venta> findById(String id);
    
    /**
     * Método que permite buscar ventas por cliente.
     */
    List<Venta> findByClienteId(String clienteId);
    
    /**
     * Método que permite buscar ventas por barbero.
     */
    List<Venta> findByBarberoId(String barberoId);
    
    /**
     * Método que permite buscar ventas por rango de fechas.
     */
    List<Venta> findByFechaBetween(LocalDateTime inicio, LocalDateTime fin);
    
    /**
     * Método que permite obtener todas las ventas.
     */
    List<Venta> findAll();
    
    /**
     * Método que permite eliminar una venta.
     */
    void deleteById(String id);
}