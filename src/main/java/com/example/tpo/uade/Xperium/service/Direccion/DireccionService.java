package com.example.tpo.uade.Xperium.service.Direccion;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.example.tpo.uade.Xperium.entity.Comprador;
import com.example.tpo.uade.Xperium.entity.Direccion;
import com.example.tpo.uade.Xperium.exceptions.CategoriaDuplicadaException;

public interface DireccionService {

    Page<Direccion> getDireccionesByCompradorId(Long compradorId, PageRequest pageRequest);

    Optional<Direccion> getDireccionesByIdAndCompradorId(Long id, Long compradorId);

    Direccion createDireccion(String calle, String numero, String departamento, String codigoPostal, Comprador comprador) throws CategoriaDuplicadaException;

    void deleteDireccionByIdAndCompradorId(Long id, Long compradorId) throws Exception;

    Direccion updateDireccion(Long id, Long compradorId, String calle, String numero, String departamento, String codigoPostal) throws Exception;
}