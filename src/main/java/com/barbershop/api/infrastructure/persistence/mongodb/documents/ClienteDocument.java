package com.barbershop.api.infrastructure.persistence.mongodb.documents;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Documento de MongoDB que representa a un Cliente.
 * Mapea la entidad de dominio Cliente a un documento en la colección "clientes".
 */
@Document(collection = "clientes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDocument {

    /**
     * Identificador único del cliente (DNI).
     */
    @Id
    private String dni;

    /**
     * Nombre del cliente.
     */
    private String nombre;

    /**
     * Correo electrónico del cliente.
     */
    private String email;

    /**
     * Contraseña del cliente.
     */
    private String password;

    /**
     * Rol del usuario (siempre "CLIENTE" para esta entidad).
     */
    private String role;

    /**
     * Edad del cliente.
     */
    private int edad;

    /**
     * Dirección del cliente.
     */
    private String direccion;

    /**
     * Género del cliente.
     */
    private String genero;

    /**
     * Teléfono del cliente.
     */
    private String telefono;
}
