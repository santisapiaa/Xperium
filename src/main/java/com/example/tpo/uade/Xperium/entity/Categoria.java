package com.example.tpo.uade.Xperium.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

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

    //@OneToMany(mappedBy = "categoriaId")
    //private List<Producto> productos;

    public Categoria() {
    }

    public Categoria(String descripcion, String imagenUrl) {
        this.descripcion = descripcion;
        this.imagenUrl = imagenUrl;
    }
}
