package com.example.tpo.uade.Xperium.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

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
public class Tarjeta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String numeroTarjeta;
    
    @Column(nullable = false)
    private String nombreTitular;
    
    @Column(nullable = false)
    private String fechaVencimiento;
    
    @Column(nullable = false)
    private String cvv;
    
    @Column(nullable = false)
    private String tipoTarjeta; // CREDITO, DEBITO
    
    @Column(columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean tarjetaPrincipal = false;

    @ManyToOne
    @JoinColumn(name = "compradorId", referencedColumnName = "id", nullable = false)
    @JsonBackReference
    private Comprador comprador;

    public Tarjeta() {
    }

    public Tarjeta(String numeroTarjeta, String nombreTitular, String fechaVencimiento, String cvv, String tipoTarjeta, Boolean tarjetaPrincipal, Comprador comprador) {
        this.numeroTarjeta = numeroTarjeta;
        this.nombreTitular = nombreTitular;
        this.fechaVencimiento = fechaVencimiento;
        this.cvv = cvv;
        this.tipoTarjeta = tipoTarjeta;
        this.tarjetaPrincipal = tarjetaPrincipal;
        this.comprador = comprador;
    }
}
