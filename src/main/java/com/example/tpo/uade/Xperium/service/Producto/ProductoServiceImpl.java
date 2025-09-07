package com.example.tpo.uade.Xperium.service.Producto;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.tpo.uade.Xperium.entity.Categoria;
import com.example.tpo.uade.Xperium.entity.Producto;
import com.example.tpo.uade.Xperium.entity.Proveedor;
import com.example.tpo.uade.Xperium.exceptions.CategoriaDuplicadaException;
import com.example.tpo.uade.Xperium.repository.ProductoRepository;

@Service
public class ProductoServiceImpl implements ProductoService {
    
    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public Page<Producto> getProducto(PageRequest pageRequest) {
        return productoRepository.findAll(pageRequest);
    }

    @Override
    public Page<Producto> getProductoByProveedorId(Long proveedorId, PageRequest pageRequest) {
        return productoRepository.findByProveedorId(proveedorId, pageRequest);
    }

    @Override
    public Optional<Producto> getProductoById(Long id) {
        return productoRepository.findById(id);
    }


    public Page<Producto> getProductoByCategoriaId(Long categoriaId,PageRequest pageRequest) {
        return productoRepository.findByCategoriaId(categoriaId,pageRequest);
    }

    @Override
    public Optional<Producto> getProductoByIdAndProveedorId(Long id, Long proveedorId) {
        return productoRepository.findByIdAndProveedorId(id, proveedorId);
    }

    @Override
    public Producto createProducto(String nombre, String descripcion, String imagenUrl, double precio, String estado, int stock, String ubicacion, int cantPersonas, Categoria categoria, Proveedor proveedor) throws CategoriaDuplicadaException {
        List<Producto> productos = productoRepository.findByNombre(nombre);
        if (productos.isEmpty()) {
            return productoRepository.save(new Producto(nombre, descripcion, imagenUrl, precio, estado, stock, ubicacion, cantPersonas, categoria, proveedor));           
        }
        throw new CategoriaDuplicadaException();
    } 
    
    @Override
    public void deleteProducto(Long id) {
        productoRepository.deleteById(id);
    }

    @Override
    public void deleteProductoByIdAndProveedorId(Long id, Long proveedorId) throws Exception {
        Optional<Producto> producto = productoRepository.findByIdAndProveedorId(id, proveedorId);
        if (producto.isPresent()) {
            productoRepository.deleteById(id);
        } else {
            throw new Exception("Producto no encontrado o no pertenece al proveedor");
        }
    }

    @Override
    public Producto updateProducto(Long id, String nombre, String descripcion, String imagenUrl, double precio, String estado, int stock, String ubicacion, int cantPersonas, Categoria categoria) throws Exception {
        Producto producto = productoRepository.findById(id)
            .orElseThrow(() -> new Exception("Producto no encontrado"));
        producto.setNombre(nombre);
        producto.setDescripcion(descripcion);
        producto.setImagenUrl(imagenUrl);
        producto.setPrecio(precio);
        producto.setEstado(estado);
        producto.setStock(stock);
        producto.setUbicacion(ubicacion);
        producto.setCantPersonas(cantPersonas);
        producto.setCategoria(categoria);
        return productoRepository.save(producto);
    }

    @Override
    public Producto updateProductoByIdAndProveedorId(Long id, Long proveedorId, String nombre, String descripcion, String imagenUrl, double precio, String estado, int stock, String ubicacion, int cantPersonas, Categoria categoria) throws Exception {
        Producto producto = productoRepository.findByIdAndProveedorId(id, proveedorId)
            .orElseThrow(() -> new Exception("Producto no encontrado o no pertenece al proveedor"));
        producto.setNombre(nombre);
        producto.setDescripcion(descripcion);
        producto.setImagenUrl(imagenUrl);
        producto.setPrecio(precio);
        producto.setEstado(estado);
        producto.setStock(stock);
        producto.setUbicacion(ubicacion);
        producto.setCantPersonas(cantPersonas);
        producto.setCategoria(categoria);
        return productoRepository.save(producto);
    }

    @Override
    @Transactional
    public Producto updateDescuento(Long id, int descuento) {
        Producto p = productoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        p.setPrecioConDescuento(descuento);
        return productoRepository.save(p);
    }

    @Override
    @Transactional
    public Producto updateDescuentoByIdAndProveedorId(Long id, Long proveedorId, int descuento) throws Exception {
        Producto p = productoRepository.findByIdAndProveedorId(id, proveedorId)
            .orElseThrow(() -> new Exception("Producto no encontrado o no pertenece al proveedor"));
        p.setPrecioConDescuento(descuento);
        return productoRepository.save(p);
    }
}