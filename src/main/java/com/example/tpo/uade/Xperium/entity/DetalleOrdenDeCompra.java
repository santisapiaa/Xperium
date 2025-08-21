/* 
package com.example.tpo.uade.Xperium.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class DetalleOrdenDeCompra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private int cantidad;
    @Column
    private double precioUnitario;

    @ManyToOne
    @JoinColumn(name = "productoId", nullable = false)
    private Producto producto;

    @ManyToOne
    @JoinColumn(name = "ordenDeCompraId", nullable = false)
    private OrdenDeCompra ordenDeCompra;
}
*/