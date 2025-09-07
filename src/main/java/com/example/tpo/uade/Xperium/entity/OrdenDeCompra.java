package com.example.tpo.uade.Xperium.entity;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
@Entity
public class OrdenDeCompra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private LocalDate fecha;
    @Column
    private double total;
    @Column
    private String estado;

    @ManyToOne
    @JoinColumn(name = "compradorId", nullable = false)
    private Comprador comprador;

    @OneToMany(mappedBy = "ordenDeCompra")
    @JsonManagedReference
    private List<DetalleOrdenDeCompra> detalleOrdenDeCompra;
    
    public OrdenDeCompra() {
    }

    public OrdenDeCompra(LocalDate fecha, double total, String estado, Comprador comprador) {
        this.fecha = fecha;
        this.total = total;
        this.estado = estado;
        this.comprador = comprador;
    }

    // Método para recalcular el total basado en los detalles
    public void recalculateTotal() {
        if (detalleOrdenDeCompra != null) {
            this.total = detalleOrdenDeCompra.stream()
                    .mapToDouble(d -> d.getCantidad() * d.getPrecioUnitario())
                    .sum();
        } else {
            this.total = 0.0;
        }
    }
    
}