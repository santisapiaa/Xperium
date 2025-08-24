package com.example.tpo.uade.Xperium.entity.dto;

import lombok.Data;

@Data
public class ProductoRequest {

    private Long id;
    private String nombre;
    private String descripcion;
    private String imagenUrl;
    private Double precio;
    private String estado;
    private Integer stock;
    private String ubicacion;
    private Integer cantPersonas;
    private CategoriaRequest categoria;
}
