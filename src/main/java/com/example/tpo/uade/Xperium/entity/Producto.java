
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
    @JoinColumn(name = "categoriaId", referencedColumnName = "id",nullable = false)
    private Categoria categoria;

    @ManyToOne
    @JoinColumn(name = "proveedorId", nullable = false)
    private Proveedor proveedor;

    //@OneToMany(mappedBy = "producto")
    //private List<DetalleOrdenDeCompra> detalleOrdenDeCompra;
    
    public Producto() {
    }

    public Producto(String nombre, String descripcion, String imagenUrl, double precio, String estado,
            int stock, String ubicacion, int cantPersonas, Categoria categoria, Proveedor proveedor) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.imagenUrl = imagenUrl;
        this.precio = precio;
        this.estado = estado;
        this.stock = stock;
        this.ubicacion = ubicacion;
        this.cantPersonas = cantPersonas;
        this.categoria = categoria;
        this.proveedor = proveedor;
    }
}