package com.barbershop.api.infrastructure.persistence.mongodb.documents;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Documento de MongoDB que representa a un Administrador.
 * Mapea la entidad de dominio Administrador a un documento en la colección "administradores".
 */
@Document(collection = "administradores")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdministradorDocument {

    /**
     * Identificador único del administrador (DNI).
     */
    @Id
    private String dni;

    /**
     * Nombre del administrador.
     */
    private String nombre;

    /**
     * Correo electrónico del administrador.
     */
    private String email;

    /**
     * Contraseña del administrador.
     */
    private String password;

    /**
     * Rol del usuario (siempre "ADMINISTRADOR" para esta entidad).
     */
    private String role;
}
