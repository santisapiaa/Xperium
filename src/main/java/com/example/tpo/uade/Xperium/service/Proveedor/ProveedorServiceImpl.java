package com.example.tpo.uade.Xperium.service.Proveedor;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.tpo.uade.Xperium.entity.Producto;
import com.example.tpo.uade.Xperium.entity.Proveedor;
import com.example.tpo.uade.Xperium.exceptions.CategoriaDuplicadaException;
import com.example.tpo.uade.Xperium.repository.ProveedorRepository;

@Service
public class ProveedorServiceImpl implements ProveedorService {

    @Autowired
    private ProveedorRepository proveedorRepository;

    public Page<Proveedor> getProveedor(PageRequest pageRequest) {
        return proveedorRepository.findAll(pageRequest);
    }

    @Override
    public Optional<Proveedor> getProveedorById(Long id) {
        return proveedorRepository.findById(id);
    }

    @Override
    public Proveedor createProveedor(String nombre, String email, String telefono, String contrasenia) throws CategoriaDuplicadaException{
        List<Proveedor> proveedor = proveedorRepository.findByNombre(nombre);
            if (proveedor.isEmpty()) {
                return proveedorRepository.save(new Proveedor(nombre, email, telefono, contrasenia));           
            }
            throw new CategoriaDuplicadaException();
    }

    @Override
    public void deleteProveedor(Long id) {
        proveedorRepository.deleteById(id);
    }

    public Proveedor updateProveedor(Long id, String nombre, String email, String telefono, String contrasenia) throws Exception {
        Proveedor proveedor = proveedorRepository.findById(id)
            .orElseThrow(() -> new Exception("Proveedor no encontrado"));
        proveedor.setNombre(nombre);
        proveedor.setEmail(email);
        proveedor.setTelefono(telefono);
        proveedor.setContrasenia(contrasenia);
        return proveedorRepository.save(proveedor);
    }    
}