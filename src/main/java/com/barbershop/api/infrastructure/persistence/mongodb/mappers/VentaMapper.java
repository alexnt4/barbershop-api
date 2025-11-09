package com.barbershop.api.infrastructure.persistence.mongodb.mappers;

import org.springframework.stereotype.Component;
import com.barbershop.api.domain.entities.Producto;
import com.barbershop.api.domain.entities.Servicio;
import com.barbershop.api.domain.entities.Usuario;
import com.barbershop.api.domain.entities.Venta;
import com.barbershop.api.domain.entities.especializaciones.*;
import com.barbershop.api.domain.repositories.UsuarioRepository;
import com.barbershop.api.infrastructure.persistence.mongodb.documents.*;
import java.math.BigDecimal;
import java.util.stream.Collectors;

@Component
public class VentaMapper {

    private final UsuarioRepository usuarioRepository;

    public VentaMapper(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public VentaDocument toDocument(final Venta venta) {
        if (venta == null) {
            return null;
        }

        return new VentaDocument(
            venta.getId(),
            venta.getFecha(),
            venta.getCliente().getDni(),
            venta.getCliente().getNombre(),
            venta.getBarbero() != null ? venta.getBarbero().getDni() : null,
            venta.getBarbero() != null ? venta.getBarbero().getNombre() : null,
            venta.getProductos().stream()
                .map(this::toProductoDocument)
                .collect(Collectors.toList()),
            venta.getServicios().stream()
                .map(this::toServicioDocument)
                .collect(Collectors.toList()),
            venta.getMontoTotal().doubleValue(),
            venta.getMetodoPago(),
            venta.getEstado()
        );
    }

    public Venta toDomain(final VentaDocument document) {
        if (document == null) {
            return null;
        }

        System.out.println("=== DEBUG VentaMapper.toDomain ===");
        System.out.println("Venta ID: " + document.getId());
        System.out.println("Productos en documento: " + (document.getProductos() != null ? document.getProductos().size() : "null"));
        System.out.println("Servicios en documento: " + (document.getServicios() != null ? document.getServicios().size() : "null"));

        Usuario cliente = usuarioRepository.findByDni(document.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado: " + document.getClienteId()));

        Usuario barbero = null;
        if (document.getBarberoId() != null) {
            barbero = usuarioRepository.findByDni(document.getBarberoId())
                    .orElse(null);
        }

        var detallesProductos = document.getProductos().stream()
                .map(this::toDetalleProductoBasic)
                .collect(Collectors.toList());

        var detallesServicios = document.getServicios().stream()
                .map(this::toDetalleServicioBasic)
                .collect(Collectors.toList());
        
        System.out.println("Detalles productos mapeados: " + detallesProductos.size());
        System.out.println("Detalles servicios mapeados: " + detallesServicios.size());

        return new Venta(
            document.getId(),
            document.getFecha(),
            cliente,
            barbero,
            detallesProductos,
            detallesServicios,
            BigDecimal.valueOf(document.getMontoTotal()),
            document.getMetodoPago(),
            document.getEstado()
        );
    }

    private DetalleProductoDocument toProductoDocument(final DetalleProducto detalle) {
        return new DetalleProductoDocument(
            detalle.getProducto().getId(),
            detalle.getProducto().getNombre(),
            detalle.getCantidad(),
            detalle.getPrecioVenta().doubleValue(), 
            detalle.getSubtotal().doubleValue()
        );
    }

    private DetalleServicioDocument toServicioDocument(final DetalleServicio detalle) {
        return new DetalleServicioDocument(
            detalle.getServicio().getId(),
            detalle.getServicio().getNombre(),
            detalle.getPrecio(), 
            detalle.getDuracion()
        );
    }

    private DetalleProducto toDetalleProductoBasic(final DetalleProductoDocument document) {
        // Crear producto básico con BigDecimal
        Producto producto = new Producto(
            document.getProductoId(),
            document.getProductoNombre(),
            "", 
            0, 
            BigDecimal.valueOf(document.getPrecioVenta()), 
            null // fechaActualizacion
        );
        return new DetalleProducto(
            producto, 
            document.getCantidad(), 
            BigDecimal.valueOf(document.getPrecioVenta()) 
        );
    }

    private DetalleServicio toDetalleServicioBasic(final DetalleServicioDocument document) {
        // Crear servicio básico
        Servicio servicio = new Servicio(
            document.getServicioId(),
            document.getServicioNombre(),
            "", // descripción
            document.getPrecio(), // precio base
            0 // duración estimada (podrías guardarla si quieres)
        );
        return new DetalleServicio(servicio, document.getPrecio(), document.getDuracion());
    }
}