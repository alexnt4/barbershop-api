package com.barbershop.api.transport.http.controllers;

import com.barbershop.api.domain.entities.Proveedor;
import com.barbershop.api.service.interactors.ProveedorInteractor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/proveedores")
@RequiredArgsConstructor
@Tag(name = "Gestión de Proveedores", description = "Endpoints para registrar, consultar, actualizar y eliminar proveedores. Además, sincroniza automáticamente el precio de compra del producto asociado.")
public class ProveedorController {

    private final ProveedorInteractor proveedorInteractor;

    @Operation(
            summary = "Registrar un nuevo proveedor",
            description = "Crea un nuevo proveedor y lo vincula con un producto existente. Si el campo 'productoId' se incluye, el sistema actualizará automáticamente el precio de compra en ese producto.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "Datos del proveedor a registrar.",
                    content = @Content(
                            schema = @Schema(implementation = Proveedor.class),
                            examples = @ExampleObject(
                                    name = "Ejemplo proveedor vinculado",
                                    value = """
                                    {
                                      "nombre": "Distribuidora BarberShop S.A.",
                                      "contacto": "Carlos Pérez",
                                      "telefono": "+57 3123456789",
                                      "email": "carlos@distribuidora.com",
                                      "condicionesEntrega": "Entrega semanal, lunes a viernes",
                                      "metodoPago": "Transferencia bancaria",
                                      "precioCompra": 25000.50,
                                      "productoId": "671b2f9154a2c84ffb13e982"
                                    }
                                    """
                            )
                    )
            )
    )
    @PostMapping
    public ResponseEntity<Proveedor> crearProveedor(@RequestBody Proveedor proveedor) {
        return ResponseEntity.ok(proveedorInteractor.crearProveedor(proveedor));
    }

    @Operation(summary = "Listar todos los proveedores")
    @GetMapping
    public ResponseEntity<List<Proveedor>> obtenerProveedores() {
        return ResponseEntity.ok(proveedorInteractor.obtenerProveedores());
    }

    @Operation(summary = "Obtener proveedor por ID")
    @GetMapping("/{id}")
    public ResponseEntity<Proveedor> obtenerProveedorPorId(@PathVariable String id) {
        return proveedorInteractor.obtenerProveedorPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Actualizar un proveedor y su vínculo con un producto",
            description = "Permite actualizar los datos del proveedor y, si cambia el producto vinculado o su precio de compra, se sincroniza automáticamente con el producto en inventario."
    )
    @PutMapping("/{id}")
    public ResponseEntity<Proveedor> actualizarProveedor(@PathVariable String id, @RequestBody Proveedor proveedor) {
        return ResponseEntity.ok(proveedorInteractor.actualizarProveedor(id, proveedor));
    }

    @Operation(summary = "Eliminar un proveedor del sistema")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProveedor(@PathVariable String id) {
        proveedorInteractor.eliminarProveedor(id);
        return ResponseEntity.noContent().build();
    }
}
