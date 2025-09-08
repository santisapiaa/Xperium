package com.example.tpo.uade.Xperium.controllers;

import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.tpo.uade.Xperium.entity.Proveedor;
import com.example.tpo.uade.Xperium.entity.dto.ProveedorRequest;
import com.example.tpo.uade.Xperium.exceptions.CategoriaDuplicadaException;
import com.example.tpo.uade.Xperium.service.Proveedor.ProveedorService;

@RestController
@RequestMapping("proveedores")
public class ProveedorController {
    
    // Inyección de dependencia del servicio de proveedor
    @Autowired
    private ProveedorService proveedorService;

    // Endpoint para obtener todos los proveedores con paginación
    @GetMapping
    public ResponseEntity<Page<Proveedor>> getProveedor(  
            @RequestParam(required = false) Integer page, // Página actual
            @RequestParam(required = false) Integer size) // Tamaño de la página
    {
        if (page == null || size == null) { // Si no se especifica página o tamaño, retornar todos los proveedores
            return ResponseEntity.ok(proveedorService.getProveedor(PageRequest.of(0, Integer.MAX_VALUE))); // Retorna todos los proveedores sin paginación
        }
        return ResponseEntity.ok(proveedorService.getProveedor(PageRequest.of(page, size))); // Retorna los proveedores con paginación
    }
    

    @GetMapping("/{proveedorId}")
    public ResponseEntity<Proveedor> getProveedorById(@PathVariable Long proveedorId) {
        Optional<Proveedor> resultado = proveedorService.getProveedorById(proveedorId);
        if (resultado.isPresent()) {
            return ResponseEntity.ok(resultado.get()); // Retorna el proveedor encontrado
        } else {
            return ResponseEntity.notFound().build(); // Retorna 404 si no se encuentra el proveedor
        }
    }

    @PostMapping
    public ResponseEntity<Object> createProveedor(@RequestBody ProveedorRequest proveedorRequest) {
        try {
            Proveedor resultado = proveedorService.createProveedor(
                proveedorRequest.getNombre(), 
                proveedorRequest.getEmail(), 
                proveedorRequest.getTelefono(), 
                proveedorRequest.getContrasenia()
            ); 
            return ResponseEntity.created(URI.create("/proveedores/" + resultado.getId())).body(resultado); // Retorna 201 Created con la ubicación del nuevo proveedor
        } catch (CategoriaDuplicadaException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateProveedor(
            @PathVariable Long id,
            @RequestBody ProveedorRequest proveedorRequest) {
        try {
            Proveedor updated = proveedorService.updateProveedor(
                id,
                proveedorRequest.getNombre(),
                proveedorRequest.getEmail(),
                proveedorRequest.getTelefono(),
                proveedorRequest.getContrasenia()
            );
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteProveedor(@PathVariable Long id) {
        Optional<Proveedor> proveedor = proveedorService.getProveedorById(id);
        if (proveedor.isPresent()) {
            proveedorService.deleteProveedor(id);
            return ResponseEntity.noContent().build(); // 204 No Content
        } else {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }
}