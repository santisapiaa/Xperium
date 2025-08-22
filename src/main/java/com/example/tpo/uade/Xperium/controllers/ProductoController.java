package com.example.tpo.uade.Xperium.controllers;

import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.tpo.uade.Xperium.entity.Categoria;
import com.example.tpo.uade.Xperium.entity.Producto;
import com.example.tpo.uade.Xperium.entity.dto.CategoriaRequest;
import com.example.tpo.uade.Xperium.entity.dto.ProductoRequest;
import com.example.tpo.uade.Xperium.exceptions.CategoriaDuplicadaException;
import com.example.tpo.uade.Xperium.service.ProductoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping
    public ResponseEntity<Page<Producto>> getProducto(
        @RequestParam(required = false) Integer page,
        @RequestParam(required = false) Integer size)
    {
        if (page == null || size == null) {
            return ResponseEntity.ok(productoService.getProducto(PageRequest.of(0,Integer.MAX_VALUE)));
        }
        return ResponseEntity.ok(productoService.getProducto(PageRequest.of(page, size)));

        }
    @GetMapping("/{productoId}")
    public ResponseEntity<Producto> getProductoById(@PathVariable Long productoId) {
        Optional<Producto> resultado = productoService.getCategoriasById(productoId);
        if (resultado.isPresent()) {
            return ResponseEntity.ok(resultado.get()); // Retorna la categoría encontrada
        } else {
            return ResponseEntity.notFound().build(); // Retorna 404 si no se encuentra la categoría
            
        }

    }

     @PostMapping
    public ResponseEntity<Object> createProdcuto(@RequestBody ProductoRequest ProductoRequest)
            throws CategoriaDuplicadaException {
        // Crea una nueva categoría
        Producto resultado = productoService.createProducto(ProductoRequest.getNombre(),ProductoRequest.getDescripcion(),ProductoRequest.getImagenUrl(),ProductoRequest.getPrecio(),ProductoRequest.getEstado(),ProductoRequest.getStock(),ProductoRequest.getUbicacion(),ProductoRequest.getCantPersonas(),ProductoRequest.getCategoriaId()); 
        return ResponseEntity.created(URI.create("/productos/" + resultado.getId())).body(resultado); // Retorna 201 Created con la ubicación de la nueva categoría
    }
    
    
}
