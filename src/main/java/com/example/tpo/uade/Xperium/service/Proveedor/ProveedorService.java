package com.example.tpo.uade.Xperium.service.Proveedor;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.example.tpo.uade.Xperium.entity.Proveedor;
import com.example.tpo.uade.Xperium.exceptions.CategoriaDuplicadaException;

public interface ProveedorService {

    public Page<Proveedor> getProveedor(PageRequest pageRequest);

    public Optional<Proveedor> getProveedorById(Long id);

    public Proveedor createProveedor(String nombre, String email, String telefono, String contrasenia) throws CategoriaDuplicadaException;
    
    public void deleteProveedor(Long id);

    public Proveedor updateProveedor(Long id, String nombre, String email, String telefono, String contrasenia) throws Exception;
  
}