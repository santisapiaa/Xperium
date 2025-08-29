package com.example.tpo.uade.Xperium.controllers;

import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.tpo.uade.Xperium.entity.Comprador;
import com.example.tpo.uade.Xperium.entity.DetalleOrdenDeCompra;
import com.example.tpo.uade.Xperium.entity.OrdenDeCompra;
import com.example.tpo.uade.Xperium.entity.dto.OrdenDeCompraRequest;
import com.example.tpo.uade.Xperium.exceptions.CategoriaDuplicadaException;
import com.example.tpo.uade.Xperium.repository.CompradorRepository;
import com.example.tpo.uade.Xperium.repository.DetalleOrdenDeCompraRepository;
import com.example.tpo.uade.Xperium.repository.OrdenDeCompraRepository;
import com.example.tpo.uade.Xperium.service.OrdenDeCompra.OrdenDeCompraService;

@RestController
@RequestMapping("ordenesDeCompra")
public class OrdenDeCompraController {

    // Inyección de dependencia del servicio de categoría
    @Autowired
    private OrdenDeCompraService ordenDeCompraService;
    @Autowired
    private DetalleOrdenDeCompraRepository detalleOrdenDeCompraRepository;
    @Autowired
    private CompradorRepository compradorRepository;

    // Endpoint para obtener todas las categorias con paginación
    @GetMapping
    public ResponseEntity<Page<OrdenDeCompra>> getOrdenesDeCompra(  
            @RequestParam(required = false) Integer page, // Página actual
            @RequestParam(required = false) Integer size) // Tamaño de la página
    {
        if (page == null || size == null) { // Si no se especifica página o tamaño, retornar todas las categorías
            return ResponseEntity.ok(ordenDeCompraService.getOrdenesDeCompra(PageRequest.of(0, Integer.MAX_VALUE))); // Retorna todas las categorías sin paginación
        }
        return ResponseEntity.ok(ordenDeCompraService.getOrdenesDeCompra(PageRequest.of(page, size))); // Retorna las categorías con paginación
    }

    @GetMapping("/{ordenDeCompraId}")
    public ResponseEntity<OrdenDeCompra> getOrdenesDeCompraById(@PathVariable Long ordenDeCompraId) {
        Optional<OrdenDeCompra> resultado = ordenDeCompraService.getOrdenesDeCompraById(ordenDeCompraId);
        if (resultado.isPresent()) {
            return ResponseEntity.ok(resultado.get()); // Retorna la categoría encontrada
        } else {
            return ResponseEntity.notFound().build(); // Retorna 404 si no se encuentra la categoría
        }
    }

    @PostMapping
        public ResponseEntity<Object> createOrdenDeCompra(@RequestBody OrdenDeCompraRequest ordenDeCompraRequest)
            throws CategoriaDuplicadaException {
        // Se crea una categoria que puede ser nula o no, si la id existe en la base de datos se asigna, sino se retorna un bad request
        Optional<Comprador> compradorOpt = compradorRepository.findById(ordenDeCompraRequest.getCompradorId());

        if (compradorOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Usuario no encontrada");
        }
        OrdenDeCompra resultado = ordenDeCompraService.createOrdenDeCompra(
            ordenDeCompraRequest.getFecha(), 
            ordenDeCompraRequest.getTotal(), 
            ordenDeCompraRequest.getEstado(),
            compradorOpt.get()
            
        );
        return ResponseEntity.created(URI.create("/ordenesDeCompra/" + resultado.getId())).body(resultado);
    }
}
