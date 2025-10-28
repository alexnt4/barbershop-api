package com.barbershop.api.infrastructure.persistence.mongodb.documents;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * Documento de MongoDB que representa a un Barbero.
 * Mapea la entidad de dominio Barbero a un documento en la colección "barberos".
 */
@Document(collection = "barberos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BarberoDocument {

    /**
     * Identificador único del barbero (DNI).
     */
    @Id
    private String dni;

    /**
     * Nombre del barbero.
     */
    private String nombre;

    /**
     * Correo electrónico del barbero.
     */
    private String email;

    /**
     * Contraseña del barbero.
     */
    private String password;

    /**
     * Rol del usuario (siempre "BARBERO" para esta entidad).
     */
    private String role;

    /**
     * Disponibilidad por día de la semana.
     * Mapa donde la clave es el día (p.ej. "MONDAY") y el valor es un mapa con "start" y "end"
     * representando los horarios en formato "HH:mm".
     */
    private Map<String, Map<String, String>> disponibilidad;

    /**
     * Lista de puntuaciones (1..5) recibidas por el barbero.
     */
    private List<Integer> ratings;
}
