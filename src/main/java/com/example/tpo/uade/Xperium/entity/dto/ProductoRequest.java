package com.example.tpo.uade.Xperium.entity.dto;

import com.example.tpo.uade.Xperium.entity.Categoria;

import lombok.Data;

@Data
public class ProductoRequest {
    private Long id;
    
    private String nombre;

    private String descripcion;

    private String imagenUrl;

    private double precio;

    private String estado;

    private int stock;

    private String ubicacion;
 
    private int cantPersonas;

    private Long categoriaId;
}
