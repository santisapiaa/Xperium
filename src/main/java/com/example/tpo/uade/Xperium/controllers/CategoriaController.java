package com.example.tpo.uade.Xperium.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.tpo.uade.Xperium.entity.Categoria;
import com.example.tpo.uade.Xperium.entity.dto.CategoriaRequest;
import com.example.tpo.uade.Xperium.exceptions.CategoriaDuplicadaException;
import com.example.tpo.uade.Xperium.service.CategoriaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;



import java.net.URI;



@RestController
@RequestMapping("categorias")
public class CategoriaController {

    // Inyección de dependencia del servicio de categoría
    @Autowired
    private CategoriaService categoriaService;



    // Endpoint para obtener todas las categorias con paginación
    @GetMapping
    public ResponseEntity<Page<Categoria>> getCategorias(  
            @RequestParam(required = false) Integer page, // Página actual
            @RequestParam(required = false) Integer size) // Tamaño de la página
    {
        if (page == null || size == null) { // Si no se especifica página o tamaño, retornar todas las categorías
            return ResponseEntity.ok(categoriaService.getCategorias(PageRequest.of(0, Integer.MAX_VALUE))); // Retorna todas las categorías sin paginación
        }
        return ResponseEntity.ok(categoriaService.getCategorias(PageRequest.of(page, size))); // Retorna las categorías con paginación
    }
    

    @GetMapping("/{categoriaId}")
    public ResponseEntity<Categoria> getCategoriaById(@PathVariable Long categoriaId) {
        Optional<Categoria> resultado = categoriaService.getCategoriasById(categoriaId);
        if (resultado.isPresent()) {
            return ResponseEntity.ok(resultado.get()); // Retorna la categoría encontrada
        } else {
            return ResponseEntity.notFound().build(); // Retorna 404 si no se encuentra la categoría
            
        }

    }

     @PostMapping
    public ResponseEntity<Object> createCategoria(@RequestBody CategoriaRequest categoriaRequest)
            throws CategoriaDuplicadaException {
        // Crea una nueva categoría
        Categoria resultado = categoriaService.createCategoria(categoriaRequest.getDescripcion(),categoriaRequest.getNombre(), categoriaRequest.getImagenUrl()); 
        return ResponseEntity.created(URI.create("/categorias/" + resultado.getId())).body(resultado); // Retorna 201 Created con la ubicación de la nueva categoría
    }
    
    
    
}
