package com.example.tpo.uade.Xperium.controllers;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.example.tpo.uade.Xperium.entity.Comprador;
import com.example.tpo.uade.Xperium.entity.Direccion;
import com.example.tpo.uade.Xperium.entity.dto.CompradorRequest;
import com.example.tpo.uade.Xperium.repository.CompradorRepository;
import com.example.tpo.uade.Xperium.service.Comprador.CompradorService;
import com.example.tpo.uade.Xperium.service.Direccion.DireccionService;

@RestController
@RequestMapping("compradores")
public class CompradorController {

    @Autowired
    private CompradorService compradorService;

    @Autowired
    private DireccionService direccionService;

    @Autowired
    private CompradorRepository compradorRepository;

    // Método para obtener el Comprador autenticado

    private Comprador getAuthenticatedComprador() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return compradorRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Comprador no encontrado en el contexto de seguridad"));
    }

    // Endpoint para obtener COMPRADOR autenticado

    @GetMapping("/micuenta")
    public ResponseEntity<Comprador> getCurrentComprador() {
        Comprador comprador = getAuthenticatedComprador();
        return ResponseEntity.ok(comprador);
    }

    // Endpoint para obtener COMPRADOR por ID

    @GetMapping("/{compradorId}")
    public ResponseEntity<Comprador> getCompradorById(@PathVariable Long compradorId) {
        Comprador authenticatedComprador = getAuthenticatedComprador();
        if (!compradorId.equals(authenticatedComprador.getId())) {
            return ResponseEntity.status(403).build(); // Forbidden
        }
        Optional<Comprador> resultado = compradorService.getCompradoresById(compradorId);
        if (resultado.isPresent()) {
            return ResponseEntity.ok(resultado.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoint para crear COMPRADOR

    @PostMapping
    public ResponseEntity<Object> createComprador(@RequestBody CompradorRequest compradorRequest) {
        try {
            Comprador resultado = compradorService.createComprador(compradorRequest.getNombre(), 
            compradorRequest.getApellido(), 
            compradorRequest.getEmail(), 
            compradorRequest.getTelefono(), 
            compradorRequest.getContrasenia());
            return ResponseEntity.created(URI.create("/compradores/" + resultado.getId())).body(resultado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Endpoint para actualizar COMPRADOR

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateComprador(@PathVariable Long id, @RequestBody CompradorRequest compradorRequest) {
        Comprador authenticatedComprador = getAuthenticatedComprador();
        try {
            Comprador updated = compradorService.updateComprador(id, authenticatedComprador.getId(), 
            compradorRequest.getNombre(), 
            compradorRequest.getApellido(), 
            compradorRequest.getEmail(), 
            compradorRequest.getTelefono());
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Endpoint para eliminar COMPRADOR

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteComprador(@PathVariable Long id) {
        Comprador authenticatedComprador = getAuthenticatedComprador();
        try {
            compradorService.deleteComprador(id, authenticatedComprador.getId());
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
