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
import com.example.tpo.uade.Xperium.entity.Producto;
import com.example.tpo.uade.Xperium.entity.dto.DetalleOrdenDeCompraRequest;
import com.example.tpo.uade.Xperium.exceptions.CategoriaDuplicadaException;
import com.example.tpo.uade.Xperium.repository.OrdenDeCompraRepository;
import com.example.tpo.uade.Xperium.repository.ProductoRepository;
import com.example.tpo.uade.Xperium.service.DetalleOrdenDeCompra.DetalleOrdenDeCompraService;

@RestController
@RequestMapping("detallesOrdenDeCompra")
public class DetalleOrdenDeCompraController {

    @Autowired
    private DetalleOrdenDeCompraService detalleOrdenDeCompraService;
    @Autowired
    private ProductoRepository productoRepository;
    @Autowired
    private OrdenDeCompraRepository ordenDeCompraRepository;

    @GetMapping
    public ResponseEntity<Page<DetalleOrdenDeCompra>> getDetallesOrdenDeCompra(
        @RequestParam(required = false) Integer page,
        @RequestParam(required = false) Integer size)
    {
        if (page == null || size == null) {
            return ResponseEntity.ok(detalleOrdenDeCompraService.getDetallesOrdenDeCompra(PageRequest.of(0,Integer.MAX_VALUE)));
        }
        return ResponseEntity.ok(detalleOrdenDeCompraService.getDetallesOrdenDeCompra(PageRequest.of(page, size)));

        }

    @GetMapping("/{detalleOrdenDeCompraId}")
    public ResponseEntity<DetalleOrdenDeCompra> getProductoById(@PathVariable Long detalleOrdenDeCompraId) {
        Optional<DetalleOrdenDeCompra> resultado = detalleOrdenDeCompraService.getDetallesOrdenDeCompraById(detalleOrdenDeCompraId);
        if (resultado.isPresent()) {
            return ResponseEntity.ok(resultado.get()); // Retorna la categoría encontrada
        } else {
            return ResponseEntity.notFound().build(); // Retorna 404 si no se encuentra la categoría
        }
    }

    @PostMapping
        public ResponseEntity<Object> createDetalleOrdenDeCompra(@RequestBody DetalleOrdenDeCompraRequest detalleOrdenDeCompraRequest)
            throws CategoriaDuplicadaException {
        // Se crea una categoria que puede ser nula o no, si la id existe en la base de datos se asigna, sino se retorna un bad request
        Optional<OrdenDeCompra> ordenDeCompraOpt = ordenDeCompraRepository.findById(detalleOrdenDeCompraRequest.getOrdenDeCompraId()); 
        if (ordenDeCompraOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("OrdenDeCompra no encontrado");
        }
        
        Optional<Producto> productoOpt = productoRepository.findById(detalleOrdenDeCompraRequest.getProductoId());
        if (productoOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Producto no encontrado");
        }

        Producto producto = productoOpt.get();
        if (producto.getStock() < detalleOrdenDeCompraRequest.getCantidad() || !"DISPONIBLE".equals(producto.getEstado())) {
            return ResponseEntity.badRequest().body("Stock insuficiente o producto no disponible");
        }

        DetalleOrdenDeCompra resultado = detalleOrdenDeCompraService.createDetalleOrdenDeCompra(
            detalleOrdenDeCompraRequest.getCantidad(),
            productoOpt.get().getPrecio()*detalleOrdenDeCompraRequest.getCantidad(),
            productoOpt.get(),
            ordenDeCompraOpt.get()
        );

        // Actualizar el stock del producto
        producto.setStock(producto.getStock() - detalleOrdenDeCompraRequest.getCantidad());
        productoRepository.save(producto);
        
        
        return ResponseEntity.created(URI.create("/detallesOrdenDeCompra/" + resultado.getId())).body(resultado);
    }
}