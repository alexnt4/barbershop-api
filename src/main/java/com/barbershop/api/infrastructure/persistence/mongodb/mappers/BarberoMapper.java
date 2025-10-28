package com.barbershop.api.infrastructure.persistence.mongodb.mappers;

import org.springframework.stereotype.Component;

import com.barbershop.api.domain.entities.especializaciones.Barbero;
import com.barbershop.api.infrastructure.persistence.mongodb.documents.BarberoDocument;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Mapper para convertir entre Barbero (dominio) y BarberoDocument (MongoDB).
 * Maneja la conversión de disponibilidad (Map con LocalTime) y ratings.
 */
@Component
public class BarberoMapper {

    /**
     * Convierte una entidad Barbero de dominio a un BarberoDocument para MongoDB.
     *
     * @param barbero entidad de dominio
     * @return documento de MongoDB
     */
    public BarberoDocument toDocument(final Barbero barbero) {
        if (barbero == null) {
            return null;
        }

        // Convertir disponibilidad: Map<DayOfWeek, TimeRange> → Map<String, Map<String, String>>
        final Map<String, Map<String, String>> disponibilidadMap = new HashMap<>();
        for (final Map.Entry<DayOfWeek, Barbero.TimeRange> entry : barbero.getDisponibilidad().entrySet()) {
            final Map<String, String> timeRangeMap = new HashMap<>();
            timeRangeMap.put("start", entry.getValue().getStart().toString());
            timeRangeMap.put("end", entry.getValue().getEnd().toString());
            disponibilidadMap.put(entry.getKey().name(), timeRangeMap);
        }

        final BarberoDocument document = new BarberoDocument();
        document.setDni(barbero.getDni());
        document.setNombre(barbero.getNombre());
        document.setEmail(barbero.getEmail().getValue());
        document.setPassword(barbero.getPassword().getValue());
        document.setRole(barbero.getRole().name());
        document.setDisponibilidad(disponibilidadMap);
        document.setRatings(barbero.getRatings());

        return document;
    }

    /**
     * Convierte un BarberoDocument de MongoDB a una entidad Barbero de dominio.
     *
     * @param document documento de MongoDB
     * @return entidad de dominio
     */
    public Barbero toDomain(final BarberoDocument document) {
        if (document == null) {
            return null;
        }

        final Barbero barbero = new Barbero(
                document.getDni(),
                document.getNombre(),
                document.getEmail(),
                document.getPassword()
        );

        // Convertir disponibilidad: Map<String, Map<String, String>> → Map<DayOfWeek, TimeRange>
        if (document.getDisponibilidad() != null) {
            for (final Map.Entry<String, Map<String, String>> entry : document.getDisponibilidad().entrySet()) {
                try {
                    final DayOfWeek dia = DayOfWeek.valueOf(entry.getKey());
                    final LocalTime inicio = LocalTime.parse(entry.getValue().get("start"));
                    final LocalTime fin = LocalTime.parse(entry.getValue().get("end"));
                    barbero.setDisponibilidad(dia, inicio, fin);
                } catch (final IllegalArgumentException e) {
                    // Ignorar si la conversión falla
                }
            }
        }

        // Agregar ratings
        if (document.getRatings() != null) {
            for (final int rating : document.getRatings()) {
                barbero.addRating(rating);
            }
        }

        return barbero;
    }
}
