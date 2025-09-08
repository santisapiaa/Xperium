package com.example.tpo.uade.Xperium.service.Comprador;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.tpo.uade.Xperium.entity.Comprador;
import com.example.tpo.uade.Xperium.entity.Proveedor;
import com.example.tpo.uade.Xperium.entity.Role;
import com.example.tpo.uade.Xperium.entity.dto.CompradorRequest;
import com.example.tpo.uade.Xperium.exceptions.CategoriaDuplicadaException;
import com.example.tpo.uade.Xperium.repository.CompradorRepository;

@Service
public class CompradorServiceImpl implements CompradorService {

    @Autowired
    private CompradorRepository compradorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Page<Comprador> getCompradores(PageRequest pageRequest) {
        return compradorRepository.findAll(pageRequest);
    }

    @Override
    public Optional<Comprador> getCompradoresById(Long id) {
        return compradorRepository.findById(id);
    }

    @Override
    public Optional<Comprador> getCompradoresByEmail(String email) {
        return compradorRepository.findByEmail(email);
    }

    @Override
    public Comprador createComprador(String nombre, String apellido, String email, String telefono, String contrasenia) throws CategoriaDuplicadaException{
        Optional<Comprador> comprador = compradorRepository.findByEmail(email);
            if (comprador.isEmpty()) {
                Comprador proveedorNuevo = Comprador.builder()
                    .nombre(nombre)
                    .apellido(apellido)
                    .email(email)
                    .telefono(telefono)
                    .contrasenia(contrasenia)
                    .role(Role.VENDEDOR) // o el rol que corresponda
                    .build();
                return compradorRepository.save(proveedorNuevo);
            }
            throw new CategoriaDuplicadaException();
    }

    @Override
    public void deleteComprador(Long id, Long authenticatedCompradorId) throws Exception {
        if (!id.equals(authenticatedCompradorId)) {
            throw new Exception("No puedes eliminar otro comprador");
        }
        Optional<Comprador> comprador = compradorRepository.findById(id);
        if (comprador.isPresent()) {
            compradorRepository.deleteById(id);
        } else {
            throw new Exception("Comprador no encontrado");
        }
    }

    @Override
    public Comprador updateComprador(Long id, Long authenticatedCompradorId, String nombre, String apellido, String email, String telefono) throws Exception {
        if (!id.equals(authenticatedCompradorId)) {
            throw new Exception("No puedes actualizar otro comprador");
        }
        Comprador comprador = compradorRepository.findById(id)
            .orElseThrow(() -> new Exception("Comprador no encontrado"));
        Optional<Comprador> existingEmail = compradorRepository.findByEmail(email);
        if (existingEmail.isPresent() && !existingEmail.get().getId().equals(id)) {
            throw new Exception("El email ya está registrado por otro comprador");
        }
        comprador.setNombre(nombre);
        comprador.setApellido(apellido);
        comprador.setEmail(email);
        comprador.setTelefono(telefono);
        return compradorRepository.save(comprador);
    }

    @Override
    public Optional<Comprador> findByEmail(String email) {
        return compradorRepository.findByEmail(email);
    }
}
