package com.barbershop.api.transport.http.controllers;

import com.barbershop.api.service.dtos.MovimientoInventarioDTO;
import com.barbershop.api.service.dtos.ProductoRegisterDTO;
import com.barbershop.api.service.dtos.ProductoResponseDTO;
import com.barbershop.api.service.interactors.ObtenerProductoPorIdInteractor;
import com.barbershop.api.service.interactors.ObtenerProductosInteractor;
import com.barbershop.api.service.interactors.ObtenerProductosStockBajoInteractor;
import com.barbershop.api.service.interactors.ProductoRegistroInteractor;
import com.barbershop.api.service.interactors.RegistrarMovimientoInventarioInteractor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * Controlador REST para la gestión de productos e inventario.
 * Proporciona endpoints para CRUD de productos y movimientos de inventario.
 */
@RestController
@RequestMapping("/api/v1/productos")
@Tag(name = "Productos", description = "Endpoints para gestión de productos e inventario")
@SecurityRequirement(name = "bearerAuth")
public class ProductoController {

    /**
     * Interactor para registrar productos.
     */
    private final ProductoRegistroInteractor productoRegistroInteractor;

    /**
     * Interactor para obtener todos los productos.
     */
    private final ObtenerProductosInteractor obtenerProductosInteractor;

    /**
     * Interactor para obtener producto por ID.
     */
    private final ObtenerProductoPorIdInteractor obtenerProductoPorIdInteractor;

    /**
     * Interactor para registrar movimientos de inventario.
     */
    private final RegistrarMovimientoInventarioInteractor registrarMovimientoInventarioInteractor;

    /**
     * Interactor para obtener productos con stock bajo.
     */
    private final ObtenerProductosStockBajoInteractor obtenerProductosStockBajoInteractor;

    /**
     * Constructor con inyección de dependencias.
     *
     * @param productoRegistroInteractorParam              Interactor de registro.
     * @param obtenerProductosInteractorParam              Interactor de listado.
     * @param obtenerProductoPorIdInteractorParam          Interactor de búsqueda.
     * @param registrarMovimientoInventarioInteractorParam Interactor de
     *                                                     movimientos.
     * @param obtenerProductosStockBajoInteractorParam     Interactor de stock bajo.
     */
    public ProductoController(
            final ProductoRegistroInteractor productoRegistroInteractorParam,
            final ObtenerProductosInteractor obtenerProductosInteractorParam,
            final ObtenerProductoPorIdInteractor obtenerProductoPorIdInteractorParam,
            final RegistrarMovimientoInventarioInteractor registrarMovimientoInventarioInteractorParam,
            final ObtenerProductosStockBajoInteractor obtenerProductosStockBajoInteractorParam) {
        this.productoRegistroInteractor = productoRegistroInteractorParam;
        this.obtenerProductosInteractor = obtenerProductosInteractorParam;
        this.obtenerProductoPorIdInteractor = obtenerProductoPorIdInteractorParam;
        this.registrarMovimientoInventarioInteractor = registrarMovimientoInventarioInteractorParam;
        this.obtenerProductosStockBajoInteractor = obtenerProductosStockBajoInteractorParam;
    }

    /**
     * Registra un nuevo producto en el inventario.
     *
     * @param dto DTO con los datos del producto.
     * @return ResponseEntity con el producto creado.
     */
    @PostMapping("/registro")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'BARBERO')")
    @Operation(summary = "Registrar producto", description = "Registra un nuevo producto en el inventario. "
            + "Solo accesible para ADMINISTRADOR y BARBERO.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Producto creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "409", description = "El producto ya existe")
    })
    public ResponseEntity<ProductoResponseDTO> registrarProducto(
            @RequestBody final ProductoRegisterDTO dto) {
        ProductoResponseDTO response = productoRegistroInteractor.execute(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Obtiene todos los productos del inventario.
     *
     * @return ResponseEntity con la lista de productos.
     */
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'BARBERO')")
    @Operation(summary = "Listar productos", description = "Obtiene todos los productos del inventario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de productos obtenida")
    })
    public ResponseEntity<List<ProductoResponseDTO>> obtenerProductos() {
        List<ProductoResponseDTO> productos = obtenerProductosInteractor.execute();
        return ResponseEntity.ok(productos);
    }

    /**
     * Obtiene un producto por su ID.
     *
     * @param id ID del producto.
     * @return ResponseEntity con el producto.
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'BARBERO')")
    @Operation(summary = "Obtener producto por ID", description = "Obtiene un producto específico por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto encontrado"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    public ResponseEntity<ProductoResponseDTO> obtenerProductoPorId(
            @Parameter(description = "ID del producto") @PathVariable final String id) {
        ProductoResponseDTO producto = obtenerProductoPorIdInteractor.execute(id);
        return ResponseEntity.ok(producto);
    }

    /**
     * Registra un movimiento de inventario (entrada o salida).
     *
     * @param dto DTO con los datos del movimiento.
     * @return ResponseEntity con el producto actualizado.
     */
    @PostMapping("/movimiento")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'BARBERO')")
    @Operation(summary = "Registrar movimiento de inventario", description = "Registra una entrada o salida de productos del inventario. "
            + "Actualiza automáticamente las existencias en tiempo real.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movimiento registrado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o stock insuficiente"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    public ResponseEntity<ProductoResponseDTO> registrarMovimiento(
            @RequestBody final MovimientoInventarioDTO dto) {
        ProductoResponseDTO response = registrarMovimientoInventarioInteractor.execute(dto);
        return ResponseEntity.ok(response);
    }

    /**
     * Obtiene productos con stock bajo.
     *
     * @param umbral Umbral opcional de stock (default: 10).
     * @return ResponseEntity con la lista de productos con stock bajo.
     */
    @GetMapping("/stock-bajo")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'BARBERO')")
    @Operation(summary = "Obtener productos con stock bajo", description = "Obtiene productos cuya cantidad disponible está por debajo del umbral especificado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de productos con stock bajo")
    })
    public ResponseEntity<List<ProductoResponseDTO>> obtenerProductosStockBajo(
            @Parameter(description = "Umbral de stock (default: 10)") @RequestParam(required = false, defaultValue = "10") final Integer umbral) {
        List<ProductoResponseDTO> productos = obtenerProductosStockBajoInteractor.execute(umbral);
        return ResponseEntity.ok(productos);
    }
}
