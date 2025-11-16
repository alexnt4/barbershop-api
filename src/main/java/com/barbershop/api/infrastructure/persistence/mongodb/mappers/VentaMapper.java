package com.barbershop.api.infrastructure.persistence.mongodb.mappers;

import org.springframework.stereotype.Component;
import com.barbershop.api.domain.entities.Producto;
import com.barbershop.api.domain.entities.Servicio;
import com.barbershop.api.domain.entities.Usuario;
import com.barbershop.api.domain.entities.Venta;
import com.barbershop.api.domain.entities.especializaciones.*;
import com.barbershop.api.domain.repositories.UsuarioRepository;
import com.barbershop.api.domain.value_objects.Role;
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
        
        Usuario cliente = usuarioRepository.findByDni(document.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado: " + document.getClienteId()));

        Usuario barbero = null;
        if (document.getBarberoId() != null && !document.getBarberoId().isEmpty()) {
            try {
                barbero = usuarioRepository.findByDni(document.getBarberoId())
                        .orElse(null);
                
                if (barbero == null) {
                    System.out.println("ADVERTENCIA: Barbero no encontrado en BD, usando datos del documento");
                    barbero = new Usuario();
                    barbero.setDni(document.getBarberoId());
                    barbero.setNombre(document.getBarberoNombre() != null ? document.getBarberoNombre() : "Barbero");
                    barbero.setRole(Role.BARBERO); 
                }
            } catch (Exception e) {
                System.out.println("Error buscando barbero: " + e.getMessage());
            }
        }

        var detallesProductos = document.getProductos().stream()
                .map(this::toDetalleProductoBasic)
                .collect(Collectors.toList());

        var detallesServicios = document.getServicios().stream()
                .map(this::toDetalleServicioBasic)
                .collect(Collectors.toList());
        

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
        Producto producto = new Producto(
            document.getProductoId(),
            document.getProductoNombre(),
            "", 
            0, 
            BigDecimal.valueOf(document.getPrecioVenta()), 
            null 
        );
        return new DetalleProducto(
            producto, 
            document.getCantidad(), 
            BigDecimal.valueOf(document.getPrecioVenta()) 
        );
    }

    private DetalleServicio toDetalleServicioBasic(final DetalleServicioDocument document) {
        Servicio servicio = new Servicio();
        servicio.setId(document.getServicioId());
        servicio.setNombre(document.getServicioNombre());
        servicio.setPrecio(document.getPrecio());
        servicio.setDuracionMinutos(document.getDuracion());
    
    return new DetalleServicio(servicio, document.getPrecio(), document.getDuracion());
    }
}
