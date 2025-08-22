package com.example.tpo.uade.Xperium.service;

import org.springframework.data.domain.PageRequest;
import com.example.tpo.uade.Xperium.entity.Categoria;
import com.example.tpo.uade.Xperium.exceptions.CategoriaDuplicadaException;

import java.util.Optional;

import org.springframework.data.domain.Page;

public interface CategoriaService {

    public Page<Categoria> getCategorias(PageRequest pageRequest);

    public Optional<Categoria> getCategoriasById(Long id);

    public Categoria createCategoria(String descripcion, String imagenUrl) throws CategoriaDuplicadaException;

    public void deleteCategoria(Long id);
    
    
}
