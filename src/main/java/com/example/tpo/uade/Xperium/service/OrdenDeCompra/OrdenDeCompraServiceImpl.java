package com.example.tpo.uade.Xperium.service.OrdenDeCompra;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.tpo.uade.Xperium.entity.Comprador;
import com.example.tpo.uade.Xperium.entity.OrdenDeCompra;
import com.example.tpo.uade.Xperium.exceptions.CategoriaDuplicadaException;
import com.example.tpo.uade.Xperium.repository.OrdenDeCompraRepository;

@Service
public class OrdenDeCompraServiceImpl implements OrdenDeCompraService {

    @Autowired
    private OrdenDeCompraRepository ordenDeCompraRepository;

    @Override
    public Page<OrdenDeCompra> getOrdenesDeCompra(PageRequest pageRequest) {
        return ordenDeCompraRepository.findAll(pageRequest);
    }

    @Override
    public Optional<OrdenDeCompra> getOrdenesDeCompraById(Long id) {
        return ordenDeCompraRepository.findById(id);
    }

    @Override
    public Optional<OrdenDeCompra> getOrdenesDeCompraByIdAndCompradorId(Long id, Long compradorId) {
        return ordenDeCompraRepository.findByIdAndCompradorId(id, compradorId);
    }

    @Override
    public Page<OrdenDeCompra> getOrdenesDeCompraByCompradorId(Long compradorId, PageRequest pageRequest) {
        return ordenDeCompraRepository.findByCompradorId(compradorId, pageRequest);
    }

    @Override
    public OrdenDeCompra createOrdenDeCompra(LocalDate fecha, double total, Comprador comprador) throws CategoriaDuplicadaException {
        return ordenDeCompraRepository.save(new OrdenDeCompra(fecha, total, comprador));
    }

    @Override
    public void deleteOrdenDeCompra(Long id) {
        ordenDeCompraRepository.deleteById(id);
    }
}
