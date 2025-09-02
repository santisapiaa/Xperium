package com.example.tpo.uade.Xperium.entity.dto;

import com.example.tpo.uade.Xperium.entity.OrdenDeCompra;
import com.example.tpo.uade.Xperium.entity.Producto;

import lombok.Data;

@Data
public class DetalleOrdenDeCompraRequest {
    private Long id;

    private int cantidad;

    private double precioUnitario;

    private Long productoId;

    private Long ordenDeCompraId;
}
