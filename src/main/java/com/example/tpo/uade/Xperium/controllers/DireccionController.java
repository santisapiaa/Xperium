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

import com.example.tpo.uade.Xperium.entity.Comprador;
import com.example.tpo.uade.Xperium.entity.Direccion;
import com.example.tpo.uade.Xperium.entity.dto.DireccionRequest;
import com.example.tpo.uade.Xperium.repository.CompradorRepository;
import com.example.tpo.uade.Xperium.service.Direccion.DireccionService;

@RestController
@RequestMapping("direcciones")
public class DireccionController {

    @Autowired
    private DireccionService direccionService;

    @Autowired
    private CompradorRepository compradorRepository;

    private Comprador getAuthenticatedComprador() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return compradorRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Comprador no encontrado en el contexto de seguridad"));
    }

    // Endpoint para obtener todas las direcciones del comprador autenticado con paginación

    @GetMapping
    public ResponseEntity<Page<Direccion>> getDirecciones(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {
        Comprador comprador = getAuthenticatedComprador();
        Page<Direccion> direcciones;
        if (page == null || size == null) {
            direcciones = direccionService.getDireccionesByCompradorId(comprador.getId(), PageRequest.of(0, Integer.MAX_VALUE));
        } else {
            direcciones = direccionService.getDireccionesByCompradorId(comprador.getId(), PageRequest.of(page, size));
        }
        return ResponseEntity.ok(direcciones);
    }

    // Endpoint para obtener Direccion por ID
    
    @GetMapping("/{direccionId}")
    public ResponseEntity<Direccion> getDireccionById(@PathVariable Long direccionId) {
        Comprador comprador = getAuthenticatedComprador();
        Optional<Direccion> resultado = direccionService.getDireccionesByIdAndCompradorId(direccionId, comprador.getId());
        if (resultado.isPresent()) {
            return ResponseEntity.ok(resultado.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    // Endpoint para crear Direccion

    @PostMapping
    public ResponseEntity<Object> createDireccion(@RequestBody DireccionRequest direccionRequest) {
        try {
            Comprador comprador = getAuthenticatedComprador();
            Direccion resultado = direccionService.createDireccion(
                direccionRequest.getCalle(),
                direccionRequest.getNumero(),
                direccionRequest.getDepartamento(),
                direccionRequest.getCodigoPostal(),
                comprador
            );
            return ResponseEntity.created(URI.create("/direcciones/" + resultado.getId())).body(resultado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Endpoint para actualizar Direccion

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateDireccion(
            @PathVariable Long id,
            @RequestBody DireccionRequest direccionRequest) {
        try {
            Comprador comprador = getAuthenticatedComprador();
            Direccion updated = direccionService.updateDireccion(
                id,
                comprador.getId(),
                direccionRequest.getCalle(),
                direccionRequest.getNumero(),
                direccionRequest.getDepartamento(),
                direccionRequest.getCodigoPostal()
            );
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Endpoint para eliminar Direccion
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteDireccion(@PathVariable Long id) {
        try {
            Comprador comprador = getAuthenticatedComprador();
            direccionService.deleteDireccionByIdAndCompradorId(id, comprador.getId());
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}