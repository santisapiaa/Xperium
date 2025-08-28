package com.example.tpo.uade.Xperium.service.Comprador;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.example.tpo.uade.Xperium.entity.Comprador;
import com.example.tpo.uade.Xperium.entity.Direccion;
import com.example.tpo.uade.Xperium.exceptions.CategoriaDuplicadaException;

public interface CompradorService {

    public Page<Comprador> getCompradores(PageRequest pageRequest);

    public Optional<Comprador> getCompradoresById(Long id);

    public Comprador createComprador(String nombre, String apellido, String email, String telefono, String contraseña, Direccion direccion) throws CategoriaDuplicadaException;

    public void deleteComprador(Long id);
}