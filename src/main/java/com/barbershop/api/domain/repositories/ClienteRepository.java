package com.barbershop.api.domain.repositories;

import java.util.Optional;

import com.barbershop.api.domain.entities.especializaciones.Cliente;

/**
 * Representa el repositorio de los clientes.
 */
public interface ClienteRepository {
    /**
     * Método que permite guardar un cliente en el repositorio.
     *
     * @param usuario
     * @return usuario
     */
    Cliente save(Cliente usuario);

    /**
     * Metodo que permite obtener un cliente por su correo.
     *
     * @param email
     * @return usuario
     */
    Optional<Cliente> findByEmail(String email);

    /**
     * Método que permite buscar un cliente por su correo electrónico.
     *
     * @param dni
     * @return usuario
     */
    Optional<Cliente> findByDni(String dni);
}
