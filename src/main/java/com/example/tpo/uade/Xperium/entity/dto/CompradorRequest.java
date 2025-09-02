package com.example.tpo.uade.Xperium.entity.dto;


import lombok.Data;

@Data
public class CompradorRequest {
    private Long id;

    private String nombre;

    private String apellido;

    private String email;

    private String telefono;

    private String contraseña;
}
