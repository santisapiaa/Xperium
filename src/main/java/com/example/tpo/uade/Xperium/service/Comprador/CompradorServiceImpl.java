package com.example.tpo.uade.Xperium.service.Comprador;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.tpo.uade.Xperium.entity.Comprador;
import com.example.tpo.uade.Xperium.entity.Direccion;
import com.example.tpo.uade.Xperium.exceptions.CategoriaDuplicadaException;
import com.example.tpo.uade.Xperium.repository.CategoriaRepository;
import com.example.tpo.uade.Xperium.repository.CompradorRepository;

@Service
public class CompradorServiceImpl implements CompradorService{
    
    @Autowired
    private CompradorRepository compradorRepository;
 
    public Page<Comprador> getCompradores(PageRequest pageRequest) {
        return compradorRepository.findAll(pageRequest);
    }

    public Optional<Comprador> getCompradoresById(Long id) {
       return compradorRepository.findById(id);
    }
   
    public Comprador createComprador(String nombre, String apellido, String email, String telefono, String contraseña) throws CategoriaDuplicadaException {
        List<Comprador> compradores = compradorRepository.findByEmail(email);
        if (compradores.isEmpty()) {
            return compradorRepository.save(new Comprador(nombre, apellido, email, telefono, contraseña)); //Guardo y retorno la nueva categoria
        }
        throw new CategoriaDuplicadaException(); //Falta ver que hacer si ya existe la categoria
    }

    public void deleteComprador(Long id) {
        compradorRepository.deleteById(id);
    }
}
