package com.example.tpo.uade.Xperium.service.Comprador;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.example.tpo.uade.Xperium.entity.Comprador;
import com.example.tpo.uade.Xperium.entity.dto.CompradorRequest;
import com.example.tpo.uade.Xperium.exceptions.CategoriaDuplicadaException;

public interface CompradorService {

    Page<Comprador> getCompradores(PageRequest pageRequest);

    Optional<Comprador> getCompradoresById(Long id);

    Optional<Comprador> getCompradoresByEmail(String email);

    Comprador createComprador(String nombre, String apellido, String email, String telefono, String contrasenia) throws CategoriaDuplicadaException;

    void deleteComprador(Long id, Long authenticatedCompradorId) throws Exception;
    
    Comprador updateComprador(Long id, Long authenticatedCompradorId, String nombre, String apellido, String email, String telefono) throws Exception;
    
    Optional<Comprador> findByEmail(String email);
}