package com.barbershop.api.domain.repositories;

import java.util.List;
import java.util.Optional;

import com.barbershop.api.domain.entities.especializaciones.Barbero;


/**
 * Representa el repiositorio de barberos.
 */
public interface BarberoRepository {
    /**
     * Método que permite guardar un barbero en el repositorio.
     *
     * @param usuario
     * @return usuario
     */
    Barbero save(Barbero usuario);

    /**
     * Metodo que permite obtener un barbero por su correo.
     *
     * @param email
     * @return usuario
     */
    Optional<Barbero> findByEmail(String email);

    /**
     * Método que permite buscar un barbero por su correo electrónico.
     *
     * @param dni
     * @return usuario
     */
    Optional<Barbero> findByDni(String dni);
    /**
     * Metodo para buscar todos los barberos.
     *
     * @return Lista de repartidores.
     */
    List<Barbero> findAll();
}