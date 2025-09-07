package com.example.tpo.uade.Xperium.service.Direccion;

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

    @Override
    public Page<Direccion> getDireccionesByCompradorId(Long compradorId, PageRequest pageRequest) {
        return direccionRepository.findByCompradorId(compradorId, pageRequest);
    }

    @Override
    public Optional<Direccion> getDireccionesByIdAndCompradorId(Long id, Long compradorId) {
        return direccionRepository.findByIdAndCompradorId(id, compradorId);
    }

    @Override
    public Direccion createDireccion(String calle, String numero, String departamento, String codigoPostal, Comprador comprador) throws CategoriaDuplicadaException {
        return direccionRepository.save(new Direccion(calle, numero, departamento, codigoPostal, comprador));
    }

    @Override
    public void deleteDireccionByIdAndCompradorId(Long id, Long compradorId) throws Exception {
        Optional<Direccion> direccion = direccionRepository.findByIdAndCompradorId(id, compradorId);
        if (direccion.isPresent()) {
            direccionRepository.deleteById(id);
        } else {
            throw new Exception("Dirección no encontrada o no pertenece al comprador");
        }
    }

    @Override
    public Direccion updateDireccion(Long id, Long compradorId, String calle, String numero, String departamento, String codigoPostal) throws Exception {
        Direccion direccion = direccionRepository.findByIdAndCompradorId(id, compradorId)
            .orElseThrow(() -> new Exception("Dirección no encontrada o no pertenece al comprador"));
        direccion.setCalle(calle);
        direccion.setNumero(numero);
        direccion.setDepartamento(departamento);
        direccion.setCodigoPostal(codigoPostal);
        return direccionRepository.save(direccion);
    }
}
