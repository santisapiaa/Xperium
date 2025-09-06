package com.example.tpo.uade.Xperium.controllers;

import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.tpo.uade.Xperium.entity.Categoria;
import com.example.tpo.uade.Xperium.entity.Producto;
import com.example.tpo.uade.Xperium.entity.Proveedor;
import com.example.tpo.uade.Xperium.entity.dto.ProductoRequest;
import com.example.tpo.uade.Xperium.exceptions.CategoriaDuplicadaException;
import com.example.tpo.uade.Xperium.repository.CategoriaRepository;
import com.example.tpo.uade.Xperium.repository.ProveedorRepository;
import com.example.tpo.uade.Xperium.service.Producto.ProductoService;

@RestController
@RequestMapping("productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;
    @Autowired
    private CategoriaRepository categoriaRepository;
    @Autowired
    private ProveedorRepository proveedorRepository;

    @GetMapping
    public ResponseEntity<Page<ProductoRequest>> getProducto(
        @RequestParam(required = false) Integer page,
        @RequestParam(required = false) Integer size)
    {
        Page<Producto> productos;
        if (page == null || size == null) {
            productos = productoService.getProducto(PageRequest.of(0, Integer.MAX_VALUE));
        } else {
            productos = productoService.getProducto(PageRequest.of(page, size));
        }
        Page<ProductoRequest> productoRequestPage = productos.map(producto -> {
            ProductoRequest dto = new ProductoRequest();
            dto.setId(producto.getId());
            dto.setNombre(producto.getNombre());
            dto.setDescripcion(producto.getDescripcion());
            dto.setImagenUrl(producto.getImagenUrl());
            dto.setPrecio(producto.getPrecio());
            dto.setEstado(producto.getEstado());
            dto.setStock(producto.getStock());
            dto.setUbicacion(producto.getUbicacion());
            dto.setCantPersonas(producto.getCantPersonas());
            dto.setDescuento(producto.getDescuento());
            dto.setCategoriaId(producto.getCategoria().getId());
            dto.setProveedorId(producto.getProveedor().getId());
            return dto;
        });
        return ResponseEntity.ok(productoRequestPage);
    }

    @GetMapping("/{productoId}")
    public ResponseEntity<ProductoRequest> getProductoById(@PathVariable Long productoId) {
        Optional<Producto> resultado = productoService.getProductoById(productoId);
        if (resultado.isPresent()) {
            Producto producto = resultado.get();
            ProductoRequest dto = new ProductoRequest();
            dto.setId(producto.getId());
            dto.setNombre(producto.getNombre());
            dto.setDescripcion(producto.getDescripcion());
            dto.setImagenUrl(producto.getImagenUrl());
            dto.setPrecio(producto.getPrecio());
            dto.setEstado(producto.getEstado());
            dto.setStock(producto.getStock());
            dto.setUbicacion(producto.getUbicacion());
            dto.setCantPersonas(producto.getCantPersonas());
            dto.setDescuento(producto.getDescuento());
            dto.setCategoriaId(producto.getCategoria().getId());
            dto.setProveedorId(producto.getProveedor().getId());
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Object> createProducto(@RequestBody ProductoRequest productoRequest) {
        Optional<Proveedor> proveedorOpt = proveedorRepository.findById(productoRequest.getProveedorId());
        Optional<Categoria> categoriaOpt = categoriaRepository.findById(productoRequest.getCategoriaId());
        
        if (categoriaOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Categoría no encontrada");
        }
        if (proveedorOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Proveedor no encontrado");
        }
        
        try {
            Producto resultado = productoService.createProducto(
                productoRequest.getNombre(),
                productoRequest.getDescripcion(),
                productoRequest.getImagenUrl(),
                productoRequest.getPrecio(),
                productoRequest.getEstado(),
                productoRequest.getStock(),
                productoRequest.getUbicacion(),
                productoRequest.getCantPersonas(),
                categoriaOpt.get(),
                proveedorOpt.get()
            );
            return ResponseEntity.created(URI.create("/productos/" + resultado.getId())).body("Producto creado con éxito");
        } catch (CategoriaDuplicadaException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Ya existe un producto con este nombre.");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoRequest> updateProducto(
        @PathVariable Long id,
        @RequestBody ProductoRequest productoRequest) {
        Optional<Categoria> categoriaOpt = categoriaRepository.findById(productoRequest.getCategoriaId());
        if (categoriaOpt.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        try {
            Producto updated = productoService.updateProducto(
                id,
                productoRequest.getNombre(),
                productoRequest.getDescripcion(),
                productoRequest.getImagenUrl(),
                productoRequest.getPrecio(),
                productoRequest.getEstado(),
                productoRequest.getStock(),
                productoRequest.getUbicacion(),
                productoRequest.getCantPersonas(),
                categoriaOpt.get()
            );
            ProductoRequest dto = new ProductoRequest();
            dto.setId(updated.getId());
            dto.setNombre(updated.getNombre());
            dto.setDescripcion(updated.getDescripcion());
            dto.setImagenUrl(updated.getImagenUrl());
            dto.setPrecio(updated.getPrecio());
            dto.setEstado(updated.getEstado());
            dto.setStock(updated.getStock());
            dto.setUbicacion(updated.getUbicacion());
            dto.setCantPersonas(updated.getCantPersonas());
            dto.setDescuento(updated.getDescuento());
            dto.setCategoriaId(updated.getCategoria().getId());
            dto.setProveedorId(updated.getProveedor().getId());
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/descuento/{id}")
    public ResponseEntity<ProductoRequest> updateDescuento(
        @PathVariable Long id,
        @RequestBody ProductoRequest productoRequest) {
        try {
            Producto productoActualizado = productoService.updateDescuento(id, productoRequest.getDescuento());
            ProductoRequest dto = new ProductoRequest();
            dto.setId(productoActualizado.getId());
            dto.setNombre(productoActualizado.getNombre());
            dto.setDescripcion(productoActualizado.getDescripcion());
            dto.setImagenUrl(productoActualizado.getImagenUrl());
            dto.setPrecio(productoActualizado.getPrecio());
            dto.setEstado(productoActualizado.getEstado());
            dto.setStock(productoActualizado.getStock());
            dto.setUbicacion(productoActualizado.getUbicacion());
            dto.setCantPersonas(productoActualizado.getCantPersonas());
            dto.setDescuento(productoActualizado.getDescuento());
            dto.setCategoriaId(productoActualizado.getCategoria().getId());
            dto.setProveedorId(productoActualizado.getProveedor().getId());
            return ResponseEntity.ok(dto);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProducto(@PathVariable Long id) {
        Optional<Producto> producto = productoService.getProductoById(id);
        if (producto.isPresent()) {
            productoService.deleteProducto(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}