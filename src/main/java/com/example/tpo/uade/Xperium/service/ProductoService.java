package com.example.tpo.uade.Xperium.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.example.tpo.uade.Xperium.entity.Categoria;
import com.example.tpo.uade.Xperium.entity.Producto;
import com.example.tpo.uade.Xperium.entity.dto.ProductoRequest;
import com.example.tpo.uade.Xperium.exceptions.CategoriaDuplicadaException;

public interface ProductoService {
    public Page<Producto> getProducto(PageRequest pageRequest);

    public Optional<Producto> getProductoById(Long id);

    public Producto createProducto(String nombre, String descripcion, String imagenUrl, double precio, String estado, int stock, String ubicacion, int cantPersonas,Categoria categoriaId) throws CategoriaDuplicadaException;
    
    public void deleteProducto(Long id);
}
