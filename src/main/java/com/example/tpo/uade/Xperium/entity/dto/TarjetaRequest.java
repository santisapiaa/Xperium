package com.example.tpo.uade.Xperium.entity.dto;

import lombok.Data;

@Data
public class TarjetaRequest {
    private Long id;
    
    private String numeroTarjeta;

    private String nombreTitular;
    
    private String fechaVencimiento;
    
    private String cvv;
    
    private String tipoTarjeta;
    
    private Boolean tarjetaPrincipal;

    private Long compradorId;
}
