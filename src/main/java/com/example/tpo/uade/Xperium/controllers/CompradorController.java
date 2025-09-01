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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.tpo.uade.Xperium.entity.Categoria;
import com.example.tpo.uade.Xperium.entity.Comprador;
import com.example.tpo.uade.Xperium.entity.dto.CategoriaRequest;
import com.example.tpo.uade.Xperium.entity.dto.CompradorRequest;
import com.example.tpo.uade.Xperium.exceptions.CategoriaDuplicadaException;

import com.example.tpo.uade.Xperium.service.Comprador.CompradorService;

@RestController
@RequestMapping("compradores")
public class CompradorController {
    // Inyección de dependencia del servicio de categoría
    @Autowired
    private CompradorService compradorService;

    // Endpoint para obtener todas las categorias con paginación
    @GetMapping
    public ResponseEntity<Page<Comprador>> getCompradores(  
            @RequestParam(required = false) Integer page, // Página actual
            @RequestParam(required = false) Integer size) // Tamaño de la página
    {
        if (page == null || size == null) { // Si no se especifica página o tamaño, retornar todas las categorías
            return ResponseEntity.ok(compradorService.getCompradores(PageRequest.of(0, Integer.MAX_VALUE))); // Retorna todas las categorías sin paginación
        }
        return ResponseEntity.ok(compradorService.getCompradores(PageRequest.of(page, size))); // Retorna las categorías con paginación
    }

    @GetMapping("/{compradorId}")
    public ResponseEntity<Comprador> getCompradoresById(@PathVariable Long compradorId) {
        Optional<Comprador> resultado = compradorService.getCompradoresById(compradorId);
        if (resultado.isPresent()) {
            return ResponseEntity.ok(resultado.get()); // Retorna la categoría encontrada
        } else {
            return ResponseEntity.notFound().build(); // Retorna 404 si no se encuentra la categoría
        }
    }

    /*  @PostMapping
    public ResponseEntity<Object> createComprador(@RequestBody CompradorRequest compradorRequest)
            throws CategoriaDuplicadaException {
        // Crea una nueva categoría
        Comprador resultado = compradorRequest.createComprador(compradorRequest.getNombre(), compradorRequest.getApellido(), compradorRequest.getEmail(),
         compradorRequest.getTelefono(), compradorRequest.getContraseña()); 
        return ResponseEntity.created(URI.create("/compradores/" + resultado.getId())).body(resultado); // Retorna 201 Created con la ubicación de la nueva categoría
    }*/

    @PostMapping
    public ResponseEntity<Object> createComprador(@RequestBody CompradorRequest compradorRequest)
        throws CategoriaDuplicadaException {
        // Crea un nuevo comprador usando el servicio
        Comprador resultado = compradorService.createComprador(compradorRequest.getNombre(), compradorRequest.getApellido(), compradorRequest.getEmail(),
         compradorRequest.getTelefono(), compradorRequest.getContraseña());
        return ResponseEntity.created(URI.create("/compradores/" + resultado.getId())).body(resultado); // Retorna 201 Created con la ubicación del nuevo comprador
    }


    @PutMapping("/{id}")
    public ResponseEntity<Comprador> updateComprador(
            @PathVariable Long id,
            @RequestBody CompradorRequest compradorRequest) {
        try {
            Comprador updated = compradorService.updateComprador(
                id,
                compradorRequest.getNombre(),
                compradorRequest.getApellido(),
                compradorRequest.getEmail(),
                compradorRequest.getTelefono(),
                compradorRequest.getContraseña()
            );
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    
}
