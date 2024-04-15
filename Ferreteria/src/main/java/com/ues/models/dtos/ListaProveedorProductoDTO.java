package com.ues.models.dtos;

/**
 *
 * @author kevin
 */
public class ListaProveedorProductoDTO {
    String proveedor;
    String producto;

    public ListaProveedorProductoDTO() {
    }

    public ListaProveedorProductoDTO(String proveedor, String producto) {
        this.proveedor = proveedor;
        this.producto = producto;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }
}
