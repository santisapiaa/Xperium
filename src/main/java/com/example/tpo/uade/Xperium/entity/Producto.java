package com.example.tpo.uade.Xperium.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
@Entity
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column
    private String nombre;
    @Column
    private String descripcion;
    @Column
    private String imagenUrl;
    @Column
    private double precio;
    @Column
    private String estado;
    @Column
    private int stock;
    @Column
    private String ubicacion;
    @Column
    private int cantPersonas;

    @ManyToOne
    @JoinColumn(name = "categoriaId", nullable = false)
    private Categoria categoria;

    @ManyToOne
    @JoinColumn(name = "vendedorId", nullable = false)
    private Proveedor vendedor;

    @OneToMany(mappedBy = "producto")
    private List<DetalleOrdenDeCompra> detalleOrdenDeCompra;
}
