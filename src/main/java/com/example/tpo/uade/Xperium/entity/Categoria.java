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

    public Categoria() {
    }

    public Categoria(String descripcion, String nombre, String imagenUrl) {
        this.descripcion = descripcion;
        this.nombre = nombre;   
        this.imagenUrl = imagenUrl;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String nombre;
    @Column
    private String descripcion;
    @Column
    private String imagenUrl;

    //@OneToMany(mappedBy = "categoriaId")
    //private List<Producto> productos;
}
