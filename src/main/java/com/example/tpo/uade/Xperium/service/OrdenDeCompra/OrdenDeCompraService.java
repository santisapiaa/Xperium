package com.example.tpo.uade.Xperium.service.OrdenDeCompra;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.example.tpo.uade.Xperium.entity.Comprador;
import com.example.tpo.uade.Xperium.entity.OrdenDeCompra;
import com.example.tpo.uade.Xperium.exceptions.CategoriaDuplicadaException;

public interface OrdenDeCompraService {

    public Page<OrdenDeCompra> getOrdenesDeCompra(PageRequest pageRequest);

    public Optional<OrdenDeCompra> getOrdenesDeCompraById(Long id);

    public Optional<OrdenDeCompra> getOrdenesDeCompraByIdAndCompradorId(Long id, Long compradorId);

    public Page<OrdenDeCompra> getOrdenesDeCompraByCompradorId(Long compradorId, PageRequest pageRequest);

    public OrdenDeCompra createOrdenDeCompra(LocalDate fecha, double total, String estado, Comprador comprador) throws CategoriaDuplicadaException;

    public void deleteOrdenDeCompra(Long id);

    public OrdenDeCompra finalizeOrdenDeCompra(Long id);
}
