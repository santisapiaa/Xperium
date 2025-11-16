package com.example.tpo.uade.Xperium.service.Tarjeta;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.tpo.uade.Xperium.entity.Comprador;
import com.example.tpo.uade.Xperium.entity.Tarjeta;
import com.example.tpo.uade.Xperium.repository.TarjetaRepository;

@Service
public class TarjetaServiceImpl implements TarjetaService {

    @Autowired
    private TarjetaRepository tarjetaRepository;

    // método para obtener tarjetas por compradorId con paginación
    @Override
    public Page<Tarjeta> getTarjetasByCompradorId(Long compradorId, PageRequest pageRequest) {
        return tarjetaRepository.findByCompradorId(compradorId, pageRequest);
    }

    // método para obtener una tarjeta por id y compradorId
    @Override
    public Optional<Tarjeta> getTarjetaByIdAndCompradorId(Long id, Long compradorId) {
        return tarjetaRepository.findByIdAndCompradorId(id, compradorId);
    }

    // método para crear una tarjeta
    @Override
    public Tarjeta createTarjeta(String numeroTarjeta, String nombreTitular, String fechaVencimiento, String cvv, String tipoTarjeta, Boolean tarjetaPrincipal, Comprador comprador) throws Exception {
        // Validar formato de número de tarjeta (básico)
        if (numeroTarjeta == null || numeroTarjeta.length() < 13 || numeroTarjeta.length() > 19) {
            throw new Exception("Número de tarjeta inválido");
        }
        return tarjetaRepository.save(new Tarjeta(numeroTarjeta, nombreTitular, fechaVencimiento, cvv, tipoTarjeta, tarjetaPrincipal, comprador));
    }

    // método para eliminar una tarjeta por id y compradorId
    @Override
    public void deleteTarjetaByIdAndCompradorId(Long id, Long compradorId) throws Exception {
        Optional<Tarjeta> tarjeta = tarjetaRepository.findByIdAndCompradorId(id, compradorId);
        if (tarjeta.isPresent()) {
            tarjetaRepository.deleteById(id);
        } else {
            throw new Exception("Tarjeta no encontrada o no pertenece al comprador");
        }
    }

    // método para actualizar una tarjeta por id y compradorId
    @Override
    public Tarjeta updateTarjeta(Long id, Long compradorId, String numeroTarjeta, String nombreTitular, String fechaVencimiento, String cvv, String tipoTarjeta, Boolean tarjetaPrincipal) throws Exception {
        Tarjeta tarjeta = tarjetaRepository.findByIdAndCompradorId(id, compradorId)
            .orElseThrow(() -> new Exception("Tarjeta no encontrada o no pertenece al comprador"));
        
        tarjeta.setNumeroTarjeta(numeroTarjeta);
        tarjeta.setNombreTitular(nombreTitular);
        tarjeta.setFechaVencimiento(fechaVencimiento);
        tarjeta.setCvv(cvv);
        tarjeta.setTipoTarjeta(tipoTarjeta);
        tarjeta.setTarjetaPrincipal(tarjetaPrincipal);
        
        return tarjetaRepository.save(tarjeta);
    }
}
