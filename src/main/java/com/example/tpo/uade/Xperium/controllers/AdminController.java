package com.example.tpo.uade.Xperium.controllers;

import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.example.tpo.uade.Xperium.entity.Admin;
import com.example.tpo.uade.Xperium.entity.Comprador;
import com.example.tpo.uade.Xperium.entity.Proveedor;
import com.example.tpo.uade.Xperium.entity.dto.AdminRequest;
import com.example.tpo.uade.Xperium.exceptions.AdminDuplicadoException;
import com.example.tpo.uade.Xperium.repository.AdminRepository;
import com.example.tpo.uade.Xperium.service.Admin.AdminService;

@RestController
@RequestMapping("admins")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private AdminRepository adminRepository;

    private Admin getAuthenticatedAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return adminRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Admin no encontrado en el contexto de seguridad"));
    }

    @GetMapping("/micuenta")
    public ResponseEntity<Admin> getCurrentAdmin() {
        Admin admin = getAuthenticatedAdmin();
        return ResponseEntity.ok(admin);
    }

    @GetMapping("/{adminId}")
    public ResponseEntity<Admin> getAdminById(@PathVariable Long adminId) {
        Admin authenticatedAdmin = getAuthenticatedAdmin();
        if (!adminId.equals(authenticatedAdmin.getId())) {
            return ResponseEntity.status(403).build();
        }
        Optional<Admin> resultado = adminService.getAdminById(adminId);
        if (resultado.isPresent()) {
            return ResponseEntity.ok(resultado.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Object> createAdmin(@RequestBody AdminRequest adminRequest) {
        try {
            Admin resultado = adminService.createAdmin(
                adminRequest.getNombre(),
                adminRequest.getEmail(),
                adminRequest.getTelefono(),
                adminRequest.getContrasenia()
            );
            return ResponseEntity.created(URI.create("/admins/" + resultado.getId())).body(resultado);
        } catch (AdminDuplicadoException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateAdmin(
            @PathVariable Long id,
            @RequestBody AdminRequest adminRequest) {
        Admin authenticatedAdmin = getAuthenticatedAdmin();
        try {
            Admin updated = adminService.updateAdmin(
                id,
                adminRequest.getNombre(),
                adminRequest.getEmail(),
                adminRequest.getTelefono(),
                adminRequest.getContrasenia()
            );
            // Solo permite que el admin autenticado actualice su propio usuario
            if (!id.equals(authenticatedAdmin.getId())) {
                return ResponseEntity.status(403).build();
            }
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteAdmin(@PathVariable Long id) {
        Admin authenticatedAdmin = getAuthenticatedAdmin();
        if (!id.equals(authenticatedAdmin.getId())) {
            return ResponseEntity.status(403).build();
        }
        Optional<Admin> admin = adminService.getAdminById(id);
        if (admin.isPresent()) {
            adminService.deleteAdmin(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoints adicionales para métodos de admin en un negocio como BigBox

    @GetMapping("/proveedores")
    public ResponseEntity<Page<Proveedor>> getAllProveedores(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size)
    {
        getAuthenticatedAdmin(); // Verifica que sea admin autenticado
        if (page == null || size == null) {
            return ResponseEntity.ok(adminService.getProveedores(PageRequest.of(0, Integer.MAX_VALUE)));
        }
        return ResponseEntity.ok(adminService.getProveedores(PageRequest.of(page, size)));
    }

    @GetMapping("/proveedores/{email}")
    public ResponseEntity<Proveedor> getProveedorByEmail(
            @PathVariable String email)
    {
        getAuthenticatedAdmin();
        Optional<Proveedor> proveedor = adminService.getProveedoresByEmail(email);
        if (proveedor.isPresent()) {
            return ResponseEntity.ok(proveedor.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/compradores")
    public ResponseEntity<Page<Comprador>> getCompradores(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size)
    {
        getAuthenticatedAdmin();
        if (page == null || size == null) {
            return ResponseEntity.ok(adminService.getCompradores(PageRequest.of(0, Integer.MAX_VALUE)));
        }
        return ResponseEntity.ok(adminService.getCompradores(PageRequest.of(page, size)));
    }

    @GetMapping("/compradores/{email}")
    public ResponseEntity<Comprador> getCompradoresByEmail(
            @PathVariable String email)
    {
        getAuthenticatedAdmin();
        Optional<Comprador> comprador = adminService.getCompradoresByEmail(email);
        if (comprador.isPresent()) {
            return ResponseEntity.ok(comprador.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}