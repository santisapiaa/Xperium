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
import com.example.tpo.uade.Xperium.entity.Tarjeta;
import com.example.tpo.uade.Xperium.entity.dto.TarjetaRequest;
import com.example.tpo.uade.Xperium.repository.CompradorRepository;
import com.example.tpo.uade.Xperium.service.Tarjeta.TarjetaService;

@RestController
@RequestMapping("tarjetas")
public class TarjetaController {

    @Autowired
    private TarjetaService tarjetaService;

    @Autowired
    private CompradorRepository compradorRepository;

    private Comprador getAuthenticatedComprador() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return compradorRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Comprador no encontrado en el contexto de seguridad"));
    }

    // Endpoint para obtener todas las tarjetas del comprador autenticado con paginación
    @GetMapping
    public ResponseEntity<Page<Tarjeta>> getTarjetas(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {
        Comprador comprador = getAuthenticatedComprador();
        Page<Tarjeta> tarjetas;
        if (page == null || size == null) {
            tarjetas = tarjetaService.getTarjetasByCompradorId(comprador.getId(), PageRequest.of(0, Integer.MAX_VALUE));
        } else {
            tarjetas = tarjetaService.getTarjetasByCompradorId(comprador.getId(), PageRequest.of(page, size));
        }
        return ResponseEntity.ok(tarjetas);
    }

    // Endpoint para obtener Tarjeta por ID
    @GetMapping("/{tarjetaId}")
    public ResponseEntity<Tarjeta> getTarjetaById(@PathVariable Long tarjetaId) {
        Comprador comprador = getAuthenticatedComprador();
        Optional<Tarjeta> resultado = tarjetaService.getTarjetaByIdAndCompradorId(tarjetaId, comprador.getId());
        if (resultado.isPresent()) {
            return ResponseEntity.ok(resultado.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoint para crear Tarjeta
    @PostMapping
    public ResponseEntity<Object> createTarjeta(@RequestBody TarjetaRequest tarjetaRequest) {
        try {
            Comprador comprador = getAuthenticatedComprador();
            Tarjeta resultado = tarjetaService.createTarjeta(
                tarjetaRequest.getNumeroTarjeta(),
                tarjetaRequest.getNombreTitular(),
                tarjetaRequest.getFechaVencimiento(),
                tarjetaRequest.getCvv(),
                tarjetaRequest.getTipoTarjeta(),
                tarjetaRequest.getTarjetaPrincipal() != null ? tarjetaRequest.getTarjetaPrincipal() : false,
                comprador
            );
            return ResponseEntity.created(URI.create("/tarjetas/" + resultado.getId())).body(resultado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Endpoint para actualizar Tarjeta
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateTarjeta(
            @PathVariable Long id,
            @RequestBody TarjetaRequest tarjetaRequest) {
        try {
            Comprador comprador = getAuthenticatedComprador();
            Tarjeta updated = tarjetaService.updateTarjeta(
                id,
                comprador.getId(),
                tarjetaRequest.getNumeroTarjeta(),
                tarjetaRequest.getNombreTitular(),
                tarjetaRequest.getFechaVencimiento(),
                tarjetaRequest.getCvv(),
                tarjetaRequest.getTipoTarjeta(),
                tarjetaRequest.getTarjetaPrincipal() != null ? tarjetaRequest.getTarjetaPrincipal() : false
            );
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Endpoint para eliminar Tarjeta
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTarjeta(@PathVariable Long id) {
        try {
            Comprador comprador = getAuthenticatedComprador();
            tarjetaService.deleteTarjetaByIdAndCompradorId(id, comprador.getId());
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
