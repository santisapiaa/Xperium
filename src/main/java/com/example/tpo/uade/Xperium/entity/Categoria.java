package com.example.tpo.uade.Xperium.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String descripcion;
    @Column
    private String imagenUrl;

    public Categoria() {
    }

    public Categoria(String descripcion, String imagenUrl) {
        this.descripcion = descripcion;
        this.imagenUrl = imagenUrl;
    }
    
}
