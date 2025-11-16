package com.barbershop.api.infrastructure.persistence.spring;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import com.barbershop.api.infrastructure.persistence.mongodb.documents.VentaDocument;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Spring Data MongoDB repository para VentaDocument.
 */
public interface VentaSpringRepository extends
 MongoRepository<VentaDocument, String> {
    
    List<VentaDocument> findByClienteId(String clienteId);
    
    List<VentaDocument> findByBarberoId(String barberoId);
    
    List<VentaDocument> findByFechaBetween(LocalDateTime inicio, LocalDateTime fin);
    
    @Query("{ 'fecha': { $gte: ?0, $lte: ?1 } }")
    List<VentaDocument> findVentasByRangoFechas(LocalDateTime inicio, LocalDateTime fin);
    
    boolean existsById(String id);
}