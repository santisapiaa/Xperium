package com.example.tpo.uade.Xperium.service.Tarjeta;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.example.tpo.uade.Xperium.entity.Comprador;
import com.example.tpo.uade.Xperium.entity.Tarjeta;

public interface TarjetaService {

    Page<Tarjeta> getTarjetasByCompradorId(Long compradorId, PageRequest pageRequest);

    Optional<Tarjeta> getTarjetaByIdAndCompradorId(Long id, Long compradorId);

    Tarjeta createTarjeta(String numeroTarjeta, String nombreTitular, String fechaVencimiento, String cvv, String tipoTarjeta, Boolean tarjetaPrincipal, Comprador comprador) throws Exception;

    void deleteTarjetaByIdAndCompradorId(Long id, Long compradorId) throws Exception;

    Tarjeta updateTarjeta(Long id, Long compradorId, String numeroTarjeta, String nombreTitular, String fechaVencimiento, String cvv, String tipoTarjeta, Boolean tarjetaPrincipal) throws Exception;
}
