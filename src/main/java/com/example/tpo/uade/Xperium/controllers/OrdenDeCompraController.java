package com.example.tpo.uade.Xperium.controllers;

import java.net.URI;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.tpo.uade.Xperium.entity.Comprador;
import com.example.tpo.uade.Xperium.entity.OrdenDeCompra;
import com.example.tpo.uade.Xperium.entity.dto.OrdenDeCompraRequest;
import com.example.tpo.uade.Xperium.repository.CompradorRepository;
import com.example.tpo.uade.Xperium.service.OrdenDeCompra.OrdenDeCompraService;

@RestController
@RequestMapping("ordenesDeCompra")
public class OrdenDeCompraController {

    @Autowired
    private OrdenDeCompraService ordenDeCompraService;

    @Autowired
    private CompradorRepository compradorRepository;

    // Método para obtener el Comprador autenticado

    private Comprador getAuthenticatedComprador() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return compradorRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Comprador no encontrado en el contexto de seguridad"));
    }

    // Endpoint para obtener las órdenes de compra del comprador autenticado con paginación

    @GetMapping
    public ResponseEntity<Page<OrdenDeCompra>> getOrdenesDeCompra(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {
        Comprador comprador = getAuthenticatedComprador();
        Page<OrdenDeCompra> ordenes;
        if (page == null || size == null) {
            ordenes = ordenDeCompraService.getOrdenesDeCompraByCompradorId(comprador.getId(), PageRequest.of(0, Integer.MAX_VALUE));
        } else {
            ordenes = ordenDeCompraService.getOrdenesDeCompraByCompradorId(comprador.getId(), PageRequest.of(page, size));
        }
        return ResponseEntity.ok(ordenes);
    }

    // Endpoint para obtener una orden de compra específica por ID, verificando que pertenezca al comprador autenticado

    @GetMapping("/{ordenDeCompraId}")
    public ResponseEntity<OrdenDeCompra> getOrdenesDeCompraById(@PathVariable Long ordenDeCompraId) {
        Comprador comprador = getAuthenticatedComprador();
        Optional<OrdenDeCompra> resultado = ordenDeCompraService.getOrdenesDeCompraByIdAndCompradorId(ordenDeCompraId, comprador.getId());
        if (resultado.isPresent()) {
            return ResponseEntity.ok(resultado.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoint para crear una nueva orden de compra, asociándola al comprador autenticado

    @PostMapping
    public ResponseEntity<Object> createOrdenDeCompra() {
        try {
            Comprador comprador = getAuthenticatedComprador();
            LocalDate fecha = LocalDate.now();

            // Verificar si hay órdenes pendientes
            Page<OrdenDeCompra> ordenesPendientes = ordenDeCompraService.getOrdenesDeCompraByCompradorId(comprador.getId(), PageRequest.of(0, Integer.MAX_VALUE));
            for (OrdenDeCompra orden : ordenesPendientes) {
                if ("PENDIENTE".equals(orden.getEstado())) {
                    return ResponseEntity.badRequest().body("Ya tienes una orden pendiente. Finalizala antes de crear una nueva.");
                }
            }

            OrdenDeCompra resultado = ordenDeCompraService.createOrdenDeCompra(
                    fecha,
                    0.0,
                    "PENDIENTE",
                    comprador
            );
            return ResponseEntity.created(URI.create("/ordenesDeCompra/" + resultado.getId())).body(resultado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Endpoint para finalizar una orden de compra
    
    @PutMapping("/{ordenDeCompraId}/finalizar")
    public ResponseEntity<Object> finalizeOrdenDeCompra(@PathVariable Long ordenDeCompraId) {
        try {
            Comprador comprador = getAuthenticatedComprador();
            Optional<OrdenDeCompra> ordenOpt = ordenDeCompraService.getOrdenesDeCompraByIdAndCompradorId(ordenDeCompraId, comprador.getId());
            if (ordenOpt.isPresent()) {
                OrdenDeCompra orden = ordenOpt.get();
                if ("PENDIENTE".equals(orden.getEstado()) && orden.getTotal() > 0) {
                    orden = ordenDeCompraService.finalizeOrdenDeCompra(ordenDeCompraId);
                    return ResponseEntity.ok(orden);
                } else {
                    return ResponseEntity.badRequest().body("La orden no está en estado PENDIENTE o no tiene productos agregados.");
                }
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}