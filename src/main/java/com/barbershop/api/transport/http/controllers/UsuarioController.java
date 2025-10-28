package com.barbershop.api.transport.http.controllers;

import com.barbershop.api.service.dtos.UsuarioRegisterDTO;
import com.barbershop.api.service.dtos.UsuarioResponseDTO;
import com.barbershop.api.service.dtos.UsuarioUpdateDTO;
import com.barbershop.api.service.interactors.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * Controller para gestión de usuarios.
 */
@RestController
@RequestMapping("/api/v1/usuarios")
@AllArgsConstructor
@Tag(name = "Usuarios", description = "Endpoints para gestión de usuarios")
public class UsuarioController {

    private final UsuarioRegistroInteractor registroInteractor;
    private final UsuarioGetByIdInteractor getByIdInteractor;
    private final UsuarioGetByEmailInteractor getByEmailInteractor;
    private final UsuarioUpdateInteractor updateInteractor;
    private final UsuarioDeleteInteractor deleteInteractor;

    /**
     * Registra un nuevo usuario (público).
     *
     * @param dto datos de registro
     * @return usuario creado
     */
    @PostMapping("/registro")
    @Operation(summary = "Registrar usuario", description = "Crea un nuevo usuario en el sistema")
    public ResponseEntity<UsuarioResponseDTO> registrar(@RequestBody final UsuarioRegisterDTO dto) {
        final UsuarioResponseDTO usuario = registroInteractor.ejecutar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
    }

    /**
     * Obtiene un usuario por su DNI.
     *
     * @param dni DNI del usuario
     * @return usuario encontrado
     */
    @GetMapping("/{dni}")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'BARBERO')")
    @Operation(summary = "Obtener usuario por DNI", description = "Busca un usuario por su DNI (requiere autenticación)")
    public ResponseEntity<UsuarioResponseDTO> obtenerPorDni(@PathVariable final String dni) {
        final UsuarioResponseDTO usuario = getByIdInteractor.ejecutar(dni);
        return ResponseEntity.ok(usuario);
    }

    /**
     * Obtiene un usuario por su email.
     *
     * @param email email del usuario
     * @return usuario encontrado
     */
    @GetMapping("/email/{email}")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'BARBERO')")
    @Operation(summary = "Obtener usuario por email", description = "Busca un usuario por su email (requiere autenticación)")
    public ResponseEntity<UsuarioResponseDTO> obtenerPorEmail(@PathVariable final String email) {
        final UsuarioResponseDTO usuario = getByEmailInteractor.ejecutar(email);
        return ResponseEntity.ok(usuario);
    }

    /**
     * Actualiza un usuario existente.
     *
     * @param dni DNI del usuario
     * @param dto datos a actualizar
     * @return usuario actualizado
     */
    @PutMapping("/{dni}")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'BARBERO', 'CLIENTE')")
    @Operation(summary = "Actualizar usuario", description = "Actualiza los datos de un usuario (requiere autenticación)")
    public ResponseEntity<UsuarioResponseDTO> actualizar(
            @PathVariable final String dni,
            @RequestBody final UsuarioUpdateDTO dto) {
        final UsuarioResponseDTO usuario = updateInteractor.ejecutar(dni, dto);
        return ResponseEntity.ok(usuario);
    }

    /**
     * Elimina un usuario.
     *
     * @param dni DNI del usuario
     * @return respuesta sin contenido
     */
    @DeleteMapping("/{dni}")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @Operation(summary = "Eliminar usuario", description = "Elimina un usuario del sistema (solo administradores)")
    public ResponseEntity<Void> eliminar(@PathVariable final String dni) {
        deleteInteractor.ejecutar(dni);
        return ResponseEntity.noContent().build();
    }
}
