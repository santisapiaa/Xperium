package com.example.tpo.uade.Xperium.service.Producto;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.example.tpo.uade.Xperium.entity.Categoria;
import com.example.tpo.uade.Xperium.entity.Producto;
import com.example.tpo.uade.Xperium.entity.Proveedor;
import com.example.tpo.uade.Xperium.exceptions.CategoriaDuplicadaException;

public interface ProductoService {
    
    public Page<Producto> getProducto(PageRequest pageRequest);

    public Page<Producto> getProductoByProveedorId(Long proveedorId, PageRequest pageRequest);

    public Optional<Producto> getProductoById(Long id);

    public Optional<Producto> getProductoByIdAndProveedorId(Long id, Long proveedorId);

    public Producto createProducto(String nombre, String descripcion, String imagenUrl, double precio, String estado, int stock, String ubicacion, int cantPersonas, Categoria categoria, Proveedor proveedor) throws CategoriaDuplicadaException;
    
    public void deleteProducto(Long id);

    public void deleteProductoByIdAndProveedorId(Long id, Long proveedorId) throws Exception;

    public Producto updateProducto(Long id, String nombre, String descripcion, String imagenUrl, double precio, String estado, int stock, String ubicacion, int cantPersonas, Categoria categoria) throws Exception;

    public Producto updateProductoByIdAndProveedorId(Long id, Long proveedorId, String nombre, String descripcion, String imagenUrl, double precio, String estado, int stock, String ubicacion, int cantPersonas, Categoria categoria) throws Exception;

    public Producto updateDescuento(Long id, int descuento);

    public Page<Producto> getProductoByCategoriaId(Long categoriaId,PageRequest pageRequest);

    public Producto updateDescuentoByIdAndProveedorId(Long id, Long proveedorId, int descuento) throws Exception;
}
