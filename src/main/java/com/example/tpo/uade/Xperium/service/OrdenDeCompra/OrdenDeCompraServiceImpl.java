package com.example.tpo.uade.Xperium.service.OrdenDeCompra;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.tpo.uade.Xperium.entity.Comprador;
import com.example.tpo.uade.Xperium.entity.DetalleOrdenDeCompra;
import com.example.tpo.uade.Xperium.entity.Direccion;
import com.example.tpo.uade.Xperium.entity.OrdenDeCompra;
import com.example.tpo.uade.Xperium.exceptions.CategoriaDuplicadaException;
import com.example.tpo.uade.Xperium.repository.OrdenDeCompraRepository;

@Service
public class OrdenDeCompraServiceImpl implements OrdenDeCompraService{

    @Autowired
    private OrdenDeCompraRepository ordenDeCompraRepository;

    public Page<OrdenDeCompra> getOrdenesDeCompra(PageRequest pageRequest) {
        return ordenDeCompraRepository.findAll(pageRequest);
    }

    public Optional<OrdenDeCompra> getOrdenesDeCompraById(Long id) {
        return ordenDeCompraRepository.findById(id);
    }

    public OrdenDeCompra createOrdenDeCompra(LocalDate fecha, double total, String estado, Comprador comprador /* , List<DetalleOrdenDeCompra> detalleOrdenDeCompra*/) throws CategoriaDuplicadaException{
        //List<OrdenDeCompra> ordenDeCompras = ordenDeCompraRepository.findBy();
        //if (ordenDeCompras.isEmpty()) {
            return ordenDeCompraRepository.save(new OrdenDeCompra(fecha, total, estado, comprador /* , detalleOrdenDeCompra*/)); //Guardo y retorno la nueva categoria
        //}
        //throw new CategoriaDuplicadaException(); //Falta ver que hacer si ya existe la categoria
    }

    public void deleteOrdenDeCompra(Long id) {
        ordenDeCompraRepository.deleteById(id);
    }
}
