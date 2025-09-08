package com.example.tpo.uade.Xperium.entity.dto;

import lombok.Data;

@Data
public class AdminRequest {
    private Long id;

    private String nombre;

    private String email;

    private String telefono;
    
    private String contrasenia;
}