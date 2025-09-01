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
import com.example.tpo.uade.Xperium.entity.dto.CategoriaRequest;
import com.example.tpo.uade.Xperium.entity.dto.ProductoRequest;
import com.example.tpo.uade.Xperium.exceptions.CategoriaDuplicadaException;
import com.example.tpo.uade.Xperium.repository.CategoriaRepository;
import com.example.tpo.uade.Xperium.repository.ProductoRepository;

@Service
public class ProductoServiceImpl implements ProductoService {
    
    @Autowired
    private ProductoRepository productoRepository;


    public Page<Producto> getProducto(PageRequest pageRequest) {
        return productoRepository.findAll(pageRequest);
    }

    public Optional<Producto> getProductoById(Long id) {
        return productoRepository.findById(id);
    }
    
    public Producto createProducto(String nombre, String descripcion, String imagenUrl, double precio, String estado, int stock, String ubicacion, int cantPersonas,Categoria categoria, Proveedor proveedor) throws CategoriaDuplicadaException {
        List<Producto> productos = productoRepository.findByNombre(nombre);
        if (productos.isEmpty()) {
            return productoRepository.save(new Producto(nombre,descripcion,imagenUrl,precio,estado
                ,stock,ubicacion,cantPersonas,categoria, proveedor));           
        }
        throw new CategoriaDuplicadaException();
    } 
    
    public void deleteProducto(Long id) {
        productoRepository.deleteById(id);
    }

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
}