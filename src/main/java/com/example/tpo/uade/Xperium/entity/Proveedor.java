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
public class Proveedor {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String nombre;
    @Column
    private String email;
    @Column
    private String telefono;
    @Column
    private String contrasenia;

    public Proveedor(String nombre, String email, String telefono, String contrasenia) {
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.contrasenia = contrasenia;
    }

    public Proveedor() {
    }
}