package com.barbershop.api.infrastructure.persistence.mongodb.documents;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Documento de MongoDB que representa a un Usuario.
 * Mapea la entidad de dominio Usuario a un documento en la colección "usuarios".
 */
@Document(collection = "usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDocument {

    /**
     * Identificador único del usuario (DNI).
     */
    @Id
    private String dni;

    /**
     * Nombre del usuario.
     */
    private String nombre;

    /**
     * Correo electrónico del usuario.
     */
    private String email;

    /**
     * Contraseña del usuario.
     */
    private String password;

    /**
     * Rol del usuario.
     */
    private String role;
}
