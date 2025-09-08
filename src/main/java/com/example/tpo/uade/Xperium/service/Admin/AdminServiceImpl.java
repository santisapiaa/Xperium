package com.example.tpo.uade.Xperium.service.Admin;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.tpo.uade.Xperium.entity.Admin;
import com.example.tpo.uade.Xperium.entity.Comprador;
import com.example.tpo.uade.Xperium.entity.Proveedor;
import com.example.tpo.uade.Xperium.entity.Role;
import com.example.tpo.uade.Xperium.exceptions.AdminDuplicadoException;
import com.example.tpo.uade.Xperium.repository.AdminRepository;
import com.example.tpo.uade.Xperium.service.Comprador.CompradorService;
import com.example.tpo.uade.Xperium.service.Proveedor.ProveedorService; // Asumimos inyección para métodos de admin

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private ProveedorService proveedorService;

    @Autowired
    private CompradorService compradorService;

    public Page<Admin> getAdmins(PageRequest pageRequest) {
        return adminRepository.findAll(pageRequest);
    }

    @Override
    public Optional<Admin> getAdminById(Long id) {
        return adminRepository.findById(id);
    }

    @Override
    public Admin createAdmin(String nombre, String email, String telefono, String contrasenia) throws AdminDuplicadoException{
        List<Admin> admins = adminRepository.findByNombre(nombre);
            if (admins.isEmpty()) {
                Admin adminNuevo = Admin.builder()
                    .nombre(nombre)
                    .email(email)
                    .telefono(telefono)
                    .contrasenia(contrasenia)
                    .role(Role.ADMIN) // Rol específico para admin
                    .build();
                return adminRepository.save(adminNuevo);
            }
            throw new AdminDuplicadoException();
    }

    @Override
    public void deleteAdmin(Long id) {
        adminRepository.deleteById(id);
    }

    public Admin updateAdmin(Long id, String nombre, String email, String telefono, String contrasenia) throws Exception {
        Admin admin = adminRepository.findById(id)
            .orElseThrow(() -> new Exception("Admin no encontrado"));
        admin.setNombre(nombre);
        admin.setEmail(email);
        admin.setTelefono(telefono);
        admin.setContrasenia(contrasenia);
        return adminRepository.save(admin);
    }

    @Override
    public Page<Proveedor> getProveedores(PageRequest pageRequest) {
        return proveedorService.getProveedor(pageRequest);
    }
    @Override
    public Page<Comprador> getCompradores(PageRequest pageRequest) {
        return compradorService.getCompradores(pageRequest);
    }

    public Optional<Proveedor> getProveedoresByEmail(String email){
        return proveedorService.getProveedorByEmail(email);
    }
    
    public Optional<Comprador> getCompradoresByEmail(String email){
        return compradorService.getCompradoresByEmail(email);
    }

}