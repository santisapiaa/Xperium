package com.example.tpo.uade.Xperium.controllers;

import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.example.tpo.uade.Xperium.entity.Proveedor;
import com.example.tpo.uade.Xperium.entity.dto.ProveedorRequest;
import com.example.tpo.uade.Xperium.exceptions.CategoriaDuplicadaException;
import com.example.tpo.uade.Xperium.repository.ProveedorRepository;
import com.example.tpo.uade.Xperium.service.Proveedor.ProveedorService;

@RestController
@RequestMapping("proveedores")
public class ProveedorController {

    @Autowired
    private ProveedorService proveedorService;

    @Autowired
    private ProveedorRepository proveedorRepository;

    // Método para obtener el Proveedor autenticado

    private Proveedor getAuthenticatedProveedor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return proveedorRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Proveedor no encontrado en el contexto de seguridad"));
    }

    // Endpoint para obtener PROVEEDOR autenticado

    @GetMapping("/micuenta")
    public ResponseEntity<Proveedor> getCurrentProveedor() {
        Proveedor proveedor = getAuthenticatedProveedor();
        return ResponseEntity.ok(proveedor);
    }

    // Endpoint para obtener PROVEEDOR por ID

    @GetMapping("/{proveedorId}")
    public ResponseEntity<Proveedor> getProveedorById(@PathVariable Long proveedorId) {
        Proveedor authenticatedProveedor = getAuthenticatedProveedor();
        if (!proveedorId.equals(authenticatedProveedor.getId())) {
            return ResponseEntity.status(403).build();
        }
        Optional<Proveedor> resultado = proveedorService.getProveedorById(proveedorId);
        if (resultado.isPresent()) {
            return ResponseEntity.ok(resultado.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoint para crear PROVEEDOR

    @PostMapping
    public ResponseEntity<Object> createProveedor(@RequestBody ProveedorRequest proveedorRequest) {
        try {
            Proveedor resultado = proveedorService.createProveedor(
                proveedorRequest.getNombre(),
                proveedorRequest.getEmail(),
                proveedorRequest.getTelefono(),
                proveedorRequest.getContrasenia()
            );
            return ResponseEntity.created(URI.create("/proveedores/" + resultado.getId())).body(resultado);
        } catch (CategoriaDuplicadaException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Endpoint para actualizar PROVEEDOR

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateProveedor(
            @PathVariable Long id,
            @RequestBody ProveedorRequest proveedorRequest) {
        Proveedor authenticatedProveedor = getAuthenticatedProveedor();
        try {
            if (!id.equals(authenticatedProveedor.getId())) {
                return ResponseEntity.status(403).build();
            }
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

    // Endpoint para eliminar PROVEEDOR
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteProveedor(@PathVariable Long id) {
        Proveedor authenticatedProveedor = getAuthenticatedProveedor();
        if (!id.equals(authenticatedProveedor.getId())) {
            return ResponseEntity.status(403).build();
        }
        Optional<Proveedor> proveedor = proveedorService.getProveedorById(id);
        if (proveedor.isPresent()) {
            proveedorService.deleteProveedor(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}