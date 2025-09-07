
package com.example.tpo.uade.Xperium.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
    @Column
    private int descuento = 0;

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

    public void setPrecioConDescuento(int nuevoDescuento) {
        if (nuevoDescuento < 0 || nuevoDescuento > 100) {
            throw new IllegalArgumentException("Descuento inválido (0 a 100).");
        }

        double precioBase = descuento > 0
            ? precio / (1 - descuento / 100.0)
            : precio;

        this.descuento = nuevoDescuento;
        this.precio = nuevoDescuento > 0
            ? precioBase * (1 - nuevoDescuento / 100.0)
            : precioBase;
    }

}