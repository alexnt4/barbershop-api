package com.barbershop.api.service.dtos;

import java.math.BigDecimal;
import java.util.List;

/**
 * DTO para el reporte completo de inventario.
 */
public class ReporteInventarioDTO {
    /**
     * Lista de productos en el inventario.
     */
    private List<ProductoReporteDTO> productos;

    /**
     * Total de productos diferentes en inventario.
     */
    private Integer totalProductos;

    /**
     * Total de unidades en inventario (suma de todas las cantidades).
     */
    private Integer totalUnidades;

    /**
     * Valor total del inventario (suma de todos los valores).
     */
    private BigDecimal valorTotalInventario;

    /**
     * Número de productos con stock bajo.
     */
    private Integer productosStockBajo;

    /**
     * Constructor sin argumentos.
     */
    public ReporteInventarioDTO() {
    }

    /**
     * Constructor con todos los campos.
     *
     * @param productosParam            Lista de productos.
     * @param totalProductosParam       Total de productos.
     * @param totalUnidadesParam        Total de unidades.
     * @param valorTotalInventarioParam Valor total.
     * @param productosStockBajoParam   Productos con stock bajo.
     */
    public ReporteInventarioDTO(final List<ProductoReporteDTO> productosParam,
            final Integer totalProductosParam,
            final Integer totalUnidadesParam,
            final BigDecimal valorTotalInventarioParam,
            final Integer productosStockBajoParam) {
        this.productos = productosParam;
        this.totalProductos = totalProductosParam;
        this.totalUnidades = totalUnidadesParam;
        this.valorTotalInventario = valorTotalInventarioParam;
        this.productosStockBajo = productosStockBajoParam;
    }

    /**
     * @return Lista de productos.
     */
    public List<ProductoReporteDTO> getProductos() {
        return productos;
    }

    /**
     * @param productosParam Lista de productos.
     */
    public void setProductos(final List<ProductoReporteDTO> productosParam) {
        this.productos = productosParam;
    }

    /**
     * @return Total de productos.
     */
    public Integer getTotalProductos() {
        return totalProductos;
    }

    /**
     * @param totalProductosParam Total de productos.
     */
    public void setTotalProductos(final Integer totalProductosParam) {
        this.totalProductos = totalProductosParam;
    }

    /**
     * @return Total de unidades.
     */
    public Integer getTotalUnidades() {
        return totalUnidades;
    }

    /**
     * @param totalUnidadesParam Total de unidades.
     */
    public void setTotalUnidades(final Integer totalUnidadesParam) {
        this.totalUnidades = totalUnidadesParam;
    }

    /**
     * @return Valor total del inventario.
     */
    public BigDecimal getValorTotalInventario() {
        return valorTotalInventario;
    }

    /**
     * @param valorTotalInventarioParam Valor total.
     */
    public void setValorTotalInventario(final BigDecimal valorTotalInventarioParam) {
        this.valorTotalInventario = valorTotalInventarioParam;
    }

    /**
     * @return Productos con stock bajo.
     */
    public Integer getProductosStockBajo() {
        return productosStockBajo;
    }

    /**
     * @param productosStockBajoParam Productos con stock bajo.
     */
    public void setProductosStockBajo(final Integer productosStockBajoParam) {
        this.productosStockBajo = productosStockBajoParam;
    }

    /**
     * DTO para el detalle de cada producto en el reporte.
     */
    public static class ProductoReporteDTO {
        /**
         * ID del producto.
         */
        private String id;

        /**
         * Nombre del producto.
         */
        private String nombre;

        /**
         * Proveedor del producto.
         */
        private String proveedor;

        /**
         * Cantidad disponible.
         */
        private Integer cantidadDisponible;

        /**
         * Precio unitario.
         */
        private BigDecimal precioUnitario;

        /**
         * Valor total del producto en inventario.
         */
        private BigDecimal valorInventario;

        /**
         * Indicador de stock bajo.
         */
        private Boolean stockBajo;

        /**
         * Constructor sin argumentos.
         */
        public ProductoReporteDTO() {
        }

        /**
         * Constructor con todos los campos.
         *
         * @param idParam                 ID del producto.
         * @param nombreParam             Nombre.
         * @param proveedorParam          Proveedor.
         * @param cantidadDisponibleParam Cantidad disponible.
         * @param precioUnitarioParam     Precio unitario.
         * @param valorInventarioParam    Valor en inventario.
         * @param stockBajoParam          Indicador de stock bajo.
         */
        public ProductoReporteDTO(final String idParam,
                final String nombreParam,
                final String proveedorParam,
                final Integer cantidadDisponibleParam,
                final BigDecimal precioUnitarioParam,
                final BigDecimal valorInventarioParam,
                final Boolean stockBajoParam) {
            this.id = idParam;
            this.nombre = nombreParam;
            this.proveedor = proveedorParam;
            this.cantidadDisponible = cantidadDisponibleParam;
            this.precioUnitario = precioUnitarioParam;
            this.valorInventario = valorInventarioParam;
            this.stockBajo = stockBajoParam;
        }

        /**
         * @return ID del producto.
         */
        public String getId() {
            return id;
        }

        /**
         * @param idParam ID del producto.
         */
        public void setId(final String idParam) {
            this.id = idParam;
        }

        /**
         * @return Nombre del producto.
         */
        public String getNombre() {
            return nombre;
        }

        /**
         * @param nombreParam Nombre del producto.
         */
        public void setNombre(final String nombreParam) {
            this.nombre = nombreParam;
        }

        /**
         * @return Proveedor.
         */
        public String getProveedor() {
            return proveedor;
        }

        /**
         * @param proveedorParam Proveedor.
         */
        public void setProveedor(final String proveedorParam) {
            this.proveedor = proveedorParam;
        }

        /**
         * @return Cantidad disponible.
         */
        public Integer getCantidadDisponible() {
            return cantidadDisponible;
        }

        /**
         * @param cantidadDisponibleParam Cantidad disponible.
         */
        public void setCantidadDisponible(final Integer cantidadDisponibleParam) {
            this.cantidadDisponible = cantidadDisponibleParam;
        }

        /**
         * @return Precio unitario.
         */
        public BigDecimal getPrecioUnitario() {
            return precioUnitario;
        }

        /**
         * @param precioUnitarioParam Precio unitario.
         */
        public void setPrecioUnitario(final BigDecimal precioUnitarioParam) {
            this.precioUnitario = precioUnitarioParam;
        }

        /**
         * @return Valor en inventario.
         */
        public BigDecimal getValorInventario() {
            return valorInventario;
        }

        /**
         * @param valorInventarioParam Valor en inventario.
         */
        public void setValorInventario(final BigDecimal valorInventarioParam) {
            this.valorInventario = valorInventarioParam;
        }

        /**
         * @return Indicador de stock bajo.
         */
        public Boolean getStockBajo() {
            return stockBajo;
        }

        /**
         * @param stockBajoParam Indicador de stock bajo.
         */
        public void setStockBajo(final Boolean stockBajoParam) {
            this.stockBajo = stockBajoParam;
        }
    }
}
