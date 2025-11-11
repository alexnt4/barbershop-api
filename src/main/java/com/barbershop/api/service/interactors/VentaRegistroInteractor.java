package com.barbershop.api.service.interactors;

import org.springframework.stereotype.Service;
import com.barbershop.api.domain.entities.especializaciones.*;
import com.barbershop.api.domain.repositories.*;
import com.barbershop.api.domain.entities.Usuario;
import com.barbershop.api.domain.entities.Venta;
import com.barbershop.api.service.dtos.VentaRegisterDTO;
import com.barbershop.api.service.dtos.VentaResponseDTO;
import com.barbershop.api.service.dtos.DetalleProductoResponseDTO;
import com.barbershop.api.service.dtos.DetalleServicioResponseDTO;

import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.util.stream.Collectors;

@Service
public class VentaRegistroInteractor {

    private final VentaRepository ventaRepository;
    private final UsuarioRepository usuarioRepository;
    private final ProductoRepository productoRepository;
    private final ServicioRepository servicioRepository;

    public VentaRegistroInteractor(VentaRepository ventaRepository,
                                  UsuarioRepository usuarioRepository,
                                  ProductoRepository productoRepository,
                                  ServicioRepository servicioRepository) {
        this.ventaRepository = ventaRepository;
        this.usuarioRepository = usuarioRepository;
        this.productoRepository = productoRepository;
        this.servicioRepository = servicioRepository;
    }

    public VentaResponseDTO registrarVenta(VentaRegisterDTO ventaDTO) {
        try {
            // Obtener cliente 
            var cliente = usuarioRepository.findByDni(ventaDTO.getClienteId())
                    .orElseThrow(() -> {
                        System.out.println("ERROR: Usuario cliente no encontrado: " + ventaDTO.getClienteId());
                        return new RuntimeException("Cliente no encontrado");
                    });
            
            // Verificar que sea un cliente
            if (!cliente.getRole().name().equals("CLIENTE")) {
                throw new RuntimeException("El usuario no es un cliente: " + ventaDTO.getClienteId());
            }
            
            System.out.println("Cliente encontrado: " + cliente.getNombre());

            // Obtener barbero (usuario con rol BARBERO)
            Usuario barbero = null;
            if (ventaDTO.getBarberoId() != null && !ventaDTO.getBarberoId().isEmpty()) {
                barbero = usuarioRepository.findByDni(ventaDTO.getBarberoId())
                        .orElseThrow(() -> new RuntimeException("Barbero no encontrado: " + ventaDTO.getBarberoId()));
                
                // Verificar que sea un barbero
                if (!barbero.getRole().name().equals("BARBERO")) {
                    throw new RuntimeException("El usuario no es un barbero: " + ventaDTO.getBarberoId());
                }
                System.out.println("Barbero encontrado: " + barbero.getNombre());
            }

            // Convertir y validar productos
            var detallesProductos = ventaDTO.getProductos().stream()
                    .map(dto -> {
                        var producto = productoRepository.findById(dto.getProductoId())
                                .orElseThrow(() -> {
                                    return new RuntimeException("Producto no encontrado: " + dto.getProductoId());
                                });

                        // Validar stock
                        if (producto.getCantidadDisponible() < dto.getCantidad()) {
                            String errorMsg = "Stock insuficiente para: " + producto.getNombre() + 
                                    ". Disponible: " + producto.getCantidadDisponible() + 
                                    ", Solicitado: " + dto.getCantidad();
                            throw new RuntimeException(errorMsg);
                        }
                        
                        // Actualizar stock
                        producto.registrarSalida(dto.getCantidad());
                        productoRepository.save(producto);
                        
                        BigDecimal precioVenta = producto.getPrecioUnitario();
                        return new DetalleProducto(producto, dto.getCantidad(), precioVenta);
                    })
                    .collect(Collectors.toList());

            // Convertir servicios
            var detallesServicios = ventaDTO.getServicios() != null ? 
                ventaDTO.getServicios().stream()
                    .map(dto -> {
                        var servicio = servicioRepository.findById(dto.getServicioId())
                                .orElseThrow(() -> {
                                    return new RuntimeException("Servicio no encontrado: " + dto.getServicioId());
                                });
                        
                        System.out.println("Servicio encontrado: " + servicio.getNombre() + 
                                         ", Precio: " + servicio.getPrecio() + 
                                         ", Duración: " + servicio.getDuracionMinutos() + "min" +
                                         ", Barberos asignados: " + servicio.getBarberosIds());
                        
                        // VALIDACIÓN DEL BARBERO
                        if (ventaDTO.getBarberoId() != null && !ventaDTO.getBarberoId().isEmpty() &&
                            servicio.getBarberosIds() != null && !servicio.getBarberosIds().isEmpty()) {
                            
                            boolean barberoHabilitado = servicio.getBarberosIds().contains(ventaDTO.getBarberoId());
                            if (!barberoHabilitado) {
                                String errorMsg = "El barbero " + ventaDTO.getBarberoId() + 
                                            " no está habilitado para realizar el servicio: " + 
                                            servicio.getNombre();
                                System.out.println("ERROR: " + errorMsg);
                                throw new RuntimeException(errorMsg);
                            }
                            System.out.println("Barbero validado para el servicio: " + ventaDTO.getBarberoId());
                        }

                        double precio = servicio.getPrecio();
                        System.out.println("PRECIO AUTOMÁTICO - Servicio: " + servicio.getNombre() + 
                                         ", Precio: " + precio + 
                                         ", Duración: " + servicio.getDuracionMinutos() + "min");
                        
                        // CORREGIDO: Usa el constructor SIN barbero
                        return new DetalleServicio(servicio, precio, servicio.getDuracionMinutos());
                    })
                    .collect(Collectors.toList()) : 
                java.util.List.<DetalleServicio>of();

            // Calcular monto total
            BigDecimal montoTotalProductos = detallesProductos.stream()
                    .map(DetalleProducto::getSubtotal)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
                    
            BigDecimal montoTotalServicios = detallesServicios.stream()
                    .map(ds -> BigDecimal.valueOf(ds.getPrecio()))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
                    
            BigDecimal montoTotal = montoTotalProductos.add(montoTotalServicios);
            System.out.println("Monto total calculado: " + montoTotal);

            // Crear venta - SIN BARBERO
            var venta = new Venta(
                    null,
                    LocalDateTime.now(),
                    cliente,    // Usuario con rol CLIENTE
                    barbero,    // Usuario con rol BARBERO o null
                    detallesProductos,
                    detallesServicios,
                    montoTotal,
                    ventaDTO.getMetodoPago(),
                    "COMPLETADA"
            );

            // Guardar venta
            System.out.println("Guardando venta en repository...");
            var ventaGuardada = ventaRepository.save(venta);
            System.out.println("Venta guardada con ID: " + ventaGuardada.getId());

            return toResponseDTO(ventaGuardada);

        } catch (Exception e) {
            System.out.println("ERROR CAPTURADO en registrarVenta: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    private VentaResponseDTO toResponseDTO(Venta venta) {
        var response = new VentaResponseDTO();
        response.setId(venta.getId());
        response.setFecha(venta.getFecha());
        response.setClienteId(venta.getCliente().getDni());
        response.setClienteNombre(venta.getCliente().getNombre());
        
        // asignar barbero
        if (venta.getBarbero() != null) {
        response.setBarberoId(venta.getBarbero().getDni());
        response.setBarberoNombre(venta.getBarbero().getNombre());
        } else {
            response.setBarberoId(null);
            response.setBarberoNombre(null);
        }
        
        // Convertir detalles de productos
        response.setProductos(venta.getProductos().stream()
            .map(dp -> {
                var dto = new DetalleProductoResponseDTO();
                dto.setProductoId(dp.getProducto().getId());
                dto.setProductoNombre(dp.getProducto().getNombre());
                dto.setCantidad(dp.getCantidad());
                dto.setPrecioVenta(dp.getPrecioVenta());
                dto.setSubtotal(dp.getSubtotal());
                return dto;
            })
            .collect(Collectors.toList()));
    
        // Convertir detalles de servicios
        response.setServicios(venta.getServicios().stream()
                .map(ds -> {
                    var dto = new DetalleServicioResponseDTO();
                    dto.setServicioId(ds.getServicio().getId());
                    dto.setServicioNombre(ds.getServicio().getNombre());
                    dto.setPrecio(ds.getPrecio());
                    dto.setDuracion(ds.getDuracion());
                    return dto;  
                })
                .collect(Collectors.toList()));
        
        response.setMontoTotal(venta.getMontoTotal());
        response.setMetodoPago(venta.getMetodoPago());
        response.setEstado(venta.getEstado());
        
        return response;
    }
}