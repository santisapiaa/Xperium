package com.example.tpo.uade.Xperium.service.DetalleOrdenDeCompra;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.example.tpo.uade.Xperium.entity.DetalleOrdenDeCompra;
import com.example.tpo.uade.Xperium.entity.OrdenDeCompra;
import com.example.tpo.uade.Xperium.entity.Producto;
import com.example.tpo.uade.Xperium.exceptions.CategoriaDuplicadaException;

public interface DetalleOrdenDeCompraService {

    public Page<DetalleOrdenDeCompra> getDetallesOrdenDeCompra(PageRequest pageRequest);

    public Optional<DetalleOrdenDeCompra> getDetallesOrdenDeCompraById(Long id);

    public DetalleOrdenDeCompra createDetalleOrdenDeCompra(int cantidad, double precioUnitario, Producto producto, OrdenDeCompra ordenDeCompra) throws CategoriaDuplicadaException;

    public void deleteDetalleOrdenDeCompra(Long id);
    
}
