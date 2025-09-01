package com.example.tpo.uade.Xperium.service.Direccion;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.example.tpo.uade.Xperium.entity.Comprador;
import com.example.tpo.uade.Xperium.entity.Direccion;
import com.example.tpo.uade.Xperium.exceptions.CategoriaDuplicadaException;

public interface DireccionService {

    public Page<Direccion> getDirecciones(PageRequest pageRequest);

    public Optional<Direccion> getDireccionesById(Long id);

    public Direccion createDireccion(String calle, String numero, String departamento, String codigoPostal,Comprador comprador) throws CategoriaDuplicadaException;

    public void deleteDireccion(Long id);

    public Direccion updateDireccion(Long id, String calle, String numero, String departamento, String codigoPostal) throws Exception;

}