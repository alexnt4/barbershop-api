package com.barbershop.api.service.interactors;

import com.barbershop.api.domain.entities.Proveedor;
import com.barbershop.api.domain.entities.Producto;
import com.barbershop.api.domain.repositories.ProveedorRepository;
import com.barbershop.api.domain.repositories.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProveedorInteractor {

    private final ProveedorRepository proveedorRepository;
    private final ProductoRepository productoRepository;

    public Proveedor crearProveedor(Proveedor proveedor) {
        Proveedor nuevo = proveedorRepository.save(proveedor);
        actualizarPrecioEnProducto(nuevo);
        return nuevo;
    }

    public List<Proveedor> obtenerProveedores() {
        return proveedorRepository.findAll();
    }

    public Optional<Proveedor> obtenerProveedorPorId(String id) {
        return proveedorRepository.findById(id);
    }

    public Proveedor actualizarProveedor(String id, Proveedor proveedorActualizado) {
        return proveedorRepository.findById(id)
                .map(proveedor -> {
                    proveedor.setNombre(proveedorActualizado.getNombre());
                    proveedor.setContacto(proveedorActualizado.getContacto());
                    proveedor.setTelefono(proveedorActualizado.getTelefono());
                    proveedor.setEmail(proveedorActualizado.getEmail());
                    proveedor.setCondicionesEntrega(proveedorActualizado.getCondicionesEntrega());
                    proveedor.setMetodoPago(proveedorActualizado.getMetodoPago());
                    proveedor.setPrecioCompra(proveedorActualizado.getPrecioCompra());
                    proveedor.setProductoId(proveedorActualizado.getProductoId());

                    Proveedor actualizado = proveedorRepository.save(proveedor);

                    // Si hay producto vinculado, sincronizar precio automáticamente
                    actualizarPrecioEnProducto(actualizado);

                    return actualizado;
                })
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));
    }

    public void eliminarProveedor(String id) {
        proveedorRepository.deleteById(id);
    }

    /**
     * Sincroniza el precio de compra del proveedor con el producto asociado.
     */
    private void actualizarPrecioEnProducto(Proveedor proveedor) {
        if (proveedor.getProductoId() != null) {
            productoRepository.findById(proveedor.getProductoId()).ifPresent(producto -> {
                //producto.setPurchasePrice(proveedor.getPrecioCompra());
                productoRepository.save(producto);
            });
        }
    }
}
