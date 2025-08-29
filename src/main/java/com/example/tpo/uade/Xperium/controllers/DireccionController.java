package com.example.tpo.uade.Xperium.controllers;

import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import org.springframework.http.ResponseEntity;

import org.springframework.transaction.annotation.Transactional;

import com.example.tpo.uade.Xperium.entity.Categoria;
import com.example.tpo.uade.Xperium.entity.Comprador;
import com.example.tpo.uade.Xperium.entity.Direccion;
import com.example.tpo.uade.Xperium.entity.Producto;
import com.example.tpo.uade.Xperium.entity.Proveedor;
import com.example.tpo.uade.Xperium.entity.dto.CategoriaRequest;
import com.example.tpo.uade.Xperium.entity.dto.DireccionRequest;
import com.example.tpo.uade.Xperium.entity.dto.ProductoRequest;
import com.example.tpo.uade.Xperium.exceptions.CategoriaDuplicadaException;
import com.example.tpo.uade.Xperium.repository.CategoriaRepository;
import com.example.tpo.uade.Xperium.repository.CompradorRepository;
import com.example.tpo.uade.Xperium.repository.DireccionRepository;
import com.example.tpo.uade.Xperium.repository.ProveedorRepository;
import com.example.tpo.uade.Xperium.service.Direccion.DireccionService;
import com.example.tpo.uade.Xperium.service.Producto.ProductoService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("direccion")
public class DireccionController {

    // Inyección de dependencia del servicio de categoría
    @Autowired
    private DireccionService direccionService;
     @Autowired
    private DireccionRepository direccionRepository;
    @Autowired
    private CompradorRepository compradorRepository;

    // Endpoint para obtener todas las categorias con paginación
    @GetMapping
    public ResponseEntity<Page<Direccion>> getDireccion(  
            @RequestParam(required = false) Integer page, // Página actual
            @RequestParam(required = false) Integer size) // Tamaño de la página
    {
        if (page == null || size == null) { // Si no se especifica página o tamaño, retornar todas las categorías
            return ResponseEntity.ok(direccionService.getDirecciones(PageRequest.of(0, Integer.MAX_VALUE))); // Retorna todas las categorías sin paginación
        }
        return ResponseEntity.ok(direccionService.getDirecciones(PageRequest.of(page, size))); // Retorna las categorías con paginación
    }

    @GetMapping("/{direccionId}")
    public ResponseEntity<Direccion> getDireccionById(@PathVariable Long direccionId) {
        Optional<Direccion> resultado = direccionService.getDireccionesById(direccionId);
        if (resultado.isPresent()) {
            return ResponseEntity.ok(resultado.get()); // Retorna la categoría encontrada
        } else {
            return ResponseEntity.notFound().build(); // Retorna 404 si no se encuentra la categoría
        }
    }

     @PostMapping
        public ResponseEntity<Object> createDireccion(@RequestBody DireccionRequest direccionRequest)
            throws CategoriaDuplicadaException {
        // Se crea una categoria que puede ser nula o no, si la id existe en la base de datos se asigna, sino se retorna un bad request
        Optional<Comprador> compradorOpt = compradorRepository.findById(direccionRequest.getCompradorId()); 
        if (compradorOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Usuario no encontrada");
        }
        Direccion resultado = direccionService.createDireccion(
            direccionRequest.getCalle(), 
            direccionRequest.getNumero(), 
            direccionRequest.getDepartamento(), 
            direccionRequest.getCodigoPostal(),
            compradorOpt.get()
            
        );
        return ResponseEntity.created(URI.create("/direccion/" + resultado.getId())).body(resultado);
    }
}
