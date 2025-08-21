package com.example.tpo.uade.Xperium.entity;

import java.time.LocalDate;
import java.util.List;

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

    @OneToMany(mappedBy = "detalleOrdenDeCompraId")
    private List<DetalleOrdenDeCompra> detalleOrdenDeCompra;

}
