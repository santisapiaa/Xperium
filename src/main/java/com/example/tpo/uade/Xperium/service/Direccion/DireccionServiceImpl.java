package com.example.tpo.uade.Xperium.service.Direccion;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.tpo.uade.Xperium.entity.Comprador;
import com.example.tpo.uade.Xperium.entity.Direccion;
import com.example.tpo.uade.Xperium.exceptions.CategoriaDuplicadaException;
import com.example.tpo.uade.Xperium.repository.DireccionRepository;

@Service
public class DireccionServiceImpl implements DireccionService {

    @Autowired
    private DireccionRepository direccionRepository;
 
    public Page<Direccion> getDirecciones(PageRequest pageRequest) {
        return direccionRepository.findAll(pageRequest);
    }

    public Optional<Direccion> getDireccionesById(Long id) {
       return direccionRepository.findById(id);
    }
   
    public Direccion createDireccion(String calle, String numero, String departamento, String codigoPostal,Comprador comprador) throws CategoriaDuplicadaException {
        //List<Direccion> direcciones = direccionRepository.findById();
        //if (direcciones.isEmpty()) {
        return direccionRepository.save(new Direccion(calle, numero, departamento, codigoPostal,comprador)); //Guardo y retorno la nueva categoria
        //}
        //throw new CategoriaDuplicadaException(); //Falta ver que hacer si ya existe la categoria
    }

    public void deleteDireccion(Long id) {
        direccionRepository.deleteById(id);
    }
}
