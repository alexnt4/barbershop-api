package com.barbershop.api.transport.http.handlers;

import com.barbershop.api.service.exceptions.BarberoNoEncontradoException;
import com.barbershop.api.service.exceptions.ProductoNoEncontradoException;
import com.barbershop.api.service.exceptions.ProductoYaExisteException;
import com.barbershop.api.service.exceptions.ServicioInvalidoException;
import com.barbershop.api.service.exceptions.UsuarioNoEncontradoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Manejador global de excepciones para la API REST.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Maneja excepciones cuando no se encuentra un usuario.
     */
    @ExceptionHandler(UsuarioNoEncontradoException.class)
    public ResponseEntity<Map<String, Object>> handleUsuarioNoEncontrado(final UsuarioNoEncontradoException ex) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    /**
     * Maneja excepciones cuando no se encuentra un barbero.
     */
    @ExceptionHandler(BarberoNoEncontradoException.class)
    public ResponseEntity<Map<String, Object>> handleBarberoNoEncontrado(final BarberoNoEncontradoException ex) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    /**
     * Maneja excepciones cuando no se encuentra un producto.
     */
    @ExceptionHandler(ProductoNoEncontradoException.class)
    public ResponseEntity<Map<String, Object>> handleProductoNoEncontrado(final ProductoNoEncontradoException ex) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
     /**
     * Maneja excepciones cuando no se encuentra un servicio.
     */
    @ExceptionHandler(ServicioInvalidoException.class)
    public ResponseEntity<Map<String, Object>> handleServicioNoEncontrado(final ServicioInvalidoException ex) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    /**
     * Maneja excepciones cuando ya existe un producto con el mismo nombre.
     */
    @ExceptionHandler(ProductoYaExisteException.class)
    public ResponseEntity<Map<String, Object>> handleProductoYaExiste(final ProductoYaExisteException ex) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.CONFLICT);
    }

    /**
     * Maneja argumentos inválidos.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgument(final IllegalArgumentException ex) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Maneja excepciones de acceso denegado (permisos).
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Map<String, Object>> handleAccessDenied(final AccessDeniedException ex) {
        return buildErrorResponse("Acceso denegado: no tienes permisos para este recurso", HttpStatus.FORBIDDEN);
    }

    /**
     * Maneja excepciones generales.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(final Exception ex) {
        return buildErrorResponse("Error interno del servidor: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Construye una respuesta de error estándar.
     */
    private ResponseEntity<Map<String, Object>> buildErrorResponse(final String message, final HttpStatus status) {
        final Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("timestamp", LocalDateTime.now());
        errorResponse.put("status", status.value());
        errorResponse.put("error", status.getReasonPhrase());
        errorResponse.put("message", message);

        return ResponseEntity.status(status).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", "Validation Failed");
        body.put("message", "Datos de entrada inválidos");
        body.put("errors", errors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }
}
