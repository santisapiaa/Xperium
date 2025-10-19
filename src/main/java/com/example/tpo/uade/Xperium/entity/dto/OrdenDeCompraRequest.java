package com.example.tpo.uade.Xperium.entity.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class OrdenDeCompraRequest {
    private Long id;

    private LocalDate fecha;

    private double total;
    

    private Long compradorId;

    //private List<Long> detalleOrdenDeCompraIds;
}
