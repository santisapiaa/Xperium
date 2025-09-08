package com.example.tpo.uade.Xperium.service.Admin;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.example.tpo.uade.Xperium.entity.Admin;
import com.example.tpo.uade.Xperium.entity.Comprador;
import com.example.tpo.uade.Xperium.entity.Proveedor;
import com.example.tpo.uade.Xperium.exceptions.AdminDuplicadoException;

public interface AdminService {

    public Page<Admin> getAdmins(PageRequest pageRequest);

    public Optional<Admin> getAdminById(Long id);

    public Admin createAdmin(String nombre, String email, String telefono, String contrasenia) throws AdminDuplicadoException;
    
    public void deleteAdmin(Long id);

    public Admin updateAdmin(Long id, String nombre, String email, String telefono, String contrasenia) throws Exception;

    public Page<Proveedor> getProveedores(PageRequest pageRequest);

    public Page<Comprador> getCompradores(PageRequest pageRequest);

    public Optional<Proveedor> getProveedoresByEmail(String email);

    public Optional<Comprador> getCompradoresByEmail(String email);
  
  
}