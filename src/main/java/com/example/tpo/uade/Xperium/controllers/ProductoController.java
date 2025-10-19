package com.example.tpo.uade.Xperium.controllers;

import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    // Método para obtener el Proveedor autenticado

    private Proveedor getAuthenticatedProveedor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return proveedorRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Proveedor no encontrado en el contexto de seguridad"));
    }

    // Endpoint para obtener PRODUCTOS con paginación

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

    // Endpoint para obtener PRODUCTOS por CATEGORIA con paginación

    @GetMapping("/{categoriaId}/categoria")
    public ResponseEntity<Page<ProductoRequest>> getProductoByCategoryId(
        @PathVariable Long categoriaId,
        @RequestParam(required = false) Integer page,
        @RequestParam(required = false) Integer size) {

        Optional<Categoria> categoria = categoriaRepository.findById(categoriaId);
        if (categoria.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        Page<Producto> productos;
        if (page == null || size == null) {
            productos = productoService.getProductoByCategoriaId(categoriaId,
             PageRequest.of(0, Integer.MAX_VALUE));
        } else {
            productos = productoService.getProductoByCategoriaId(categoriaId,
             PageRequest.of(page, size));
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

    // Endpoint para obtener PRODUCTOS del PROVEEDOR autenticado con paginación

    @GetMapping("/misproductos")
    public ResponseEntity<Page<ProductoRequest>> getMisProducto(
        @RequestParam(required = false) Integer page,
        @RequestParam(required = false) Integer size)
    {
        Proveedor proveedor = getAuthenticatedProveedor();
        Page<Producto> productos;
        if (page == null || size == null) {
            productos = productoService.getProductoByProveedorId(proveedor.getId(), PageRequest.of(0, Integer.MAX_VALUE));
        } else {
            productos = productoService.getProductoByProveedorId(proveedor.getId(), PageRequest.of(page, size));
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

    // Endpoint para obtener PRODUCTO por ID del PROVEEDOR autenticado

    @GetMapping("/{productoId}")
    public ResponseEntity<ProductoRequest> getProductoById(@PathVariable Long productoId) {
        Proveedor proveedor = getAuthenticatedProveedor();
        Optional<Producto> resultado = productoService.getProductoByIdAndProveedorId(productoId, proveedor.getId());
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

    // Endpoint para crear PRODUCTO asociado al PROVEEDOR autenticado

    @PostMapping
    public ResponseEntity<Object> createProducto(@RequestBody ProductoRequest productoRequest) {
        Proveedor proveedor = getAuthenticatedProveedor();
        Optional<Categoria> categoriaOpt = categoriaRepository.findById(productoRequest.getCategoriaId());
        
        if (categoriaOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Categoría no encontrada");
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
                proveedor // Usar el proveedor autenticado
            );
            
            // Apply discount if provided
            if (productoRequest.getDescuento() > 0) {
                resultado.setPrecioConDescuento(productoRequest.getDescuento());
                productoService.updateProducto(
                    resultado.getId(),
                    resultado.getNombre(),
                    resultado.getDescripcion(),
                    resultado.getImagenUrl(),
                    resultado.getPrecio(),
                    resultado.getEstado(),
                    resultado.getStock(),
                    resultado.getUbicacion(),
                    resultado.getCantPersonas(),
                    resultado.getCategoria()
                );
            }
            
            return ResponseEntity.created(URI.create("/productos/" + resultado.getId())).body("Producto creado con éxito");
        } catch (CategoriaDuplicadaException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Ya existe un producto con este nombre.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear producto: " + e.getMessage());
        }
    }

    // Endpoint para actualizar PRODUCTO del PROVEEDOR autenticado

    @PutMapping("/{id}")
    public ResponseEntity<ProductoRequest> updateProducto(
        @PathVariable Long id,
        @RequestBody ProductoRequest productoRequest) {
        Proveedor proveedor = getAuthenticatedProveedor();
        Optional<Categoria> categoriaOpt = categoriaRepository.findById(productoRequest.getCategoriaId());
        if (categoriaOpt.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        try {
            Optional<Producto> productoOpt = productoService.getProductoByIdAndProveedorId(id, proveedor.getId());
            if (productoOpt.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            
            Producto producto = productoOpt.get();
            
            // Apply discount to the new price
            if (productoRequest.getDescuento() >= 0) {
                producto.setPrecio(productoRequest.getPrecio());
                producto.setDescuento(0); // Reset discount first
                if (productoRequest.getDescuento() > 0) {
                    producto.setPrecioConDescuento(productoRequest.getDescuento());
                }
            }
            
            // Update other fields
            Producto updated = productoService.updateProductoByIdAndProveedorId(
                id,
                proveedor.getId(),
                productoRequest.getNombre(),
                productoRequest.getDescripcion(),
                productoRequest.getImagenUrl(),
                producto.getPrecio(), // Use the price after discount calculation
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

    // Endpoint para actualizar DESCUENTO del PRODUCTO del PROVEEDOR autenticado

    @PutMapping("/descuento/{id}")
    public ResponseEntity<ProductoRequest> updateDescuento(
        @PathVariable Long id,
        @RequestBody ProductoRequest productoRequest) {
        Proveedor proveedor = getAuthenticatedProveedor();
        try {
            Producto productoActualizado = productoService.updateDescuentoByIdAndProveedorId(id, proveedor.getId(), productoRequest.getDescuento());
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
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoint para eliminar PRODUCTO del PROVEEDOR autenticado

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProducto(@PathVariable Long id) {
        Proveedor proveedor = getAuthenticatedProveedor();
        try {
            productoService.deleteProductoByIdAndProveedorId(id, proveedor.getId());
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
