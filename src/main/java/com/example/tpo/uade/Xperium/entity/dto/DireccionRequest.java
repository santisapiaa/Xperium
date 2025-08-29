package com.example.tpo.uade.Xperium.entity.dto;

import com.example.tpo.uade.Xperium.entity.Comprador;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class DireccionRequest {
    private Long id;
    
    private String calle;

    private String numero;
    
    private String departamento;
    
    private String codigoPostal;

    private Long compradorId;

}
