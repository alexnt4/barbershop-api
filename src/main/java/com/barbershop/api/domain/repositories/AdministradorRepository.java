package com.barbershop.api.domain.repositories;

import java.util.Optional;

import com.barbershop.api.domain.entities.especializaciones.Administrador;

/**
 * Representa el repositorio de administradores.
 */
public interface AdministradorRepository {
    /**
     * Método que permite guardar un administrador en el repositorio.
     *
     * @param usuario
     * @return usuario
     */
    Administrador save(Administrador usuario);

    /**
     * Metodo que permite obtener un administrador por su correo.
     *
     * @param email
     * @return usuario
     */
    Optional<Administrador> findByEmail(String email);

    /**
     * Método que permite buscar un administrador por su correo electrónico.
     *
     * @param dni
     * @return usuario
     */
    Optional<Administrador> findByDni(String dni);
}