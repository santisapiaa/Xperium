package com.example.tpo.uade.Xperium.service.DetalleOrdenDeCompra;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.tpo.uade.Xperium.entity.Comprador;
import com.example.tpo.uade.Xperium.entity.DetalleOrdenDeCompra;
import com.example.tpo.uade.Xperium.entity.OrdenDeCompra;
import com.example.tpo.uade.Xperium.entity.Producto;
import com.example.tpo.uade.Xperium.exceptions.CategoriaDuplicadaException;
import com.example.tpo.uade.Xperium.repository.DetalleOrdenDeCompraRepository;
import com.example.tpo.uade.Xperium.repository.OrdenDeCompraRepository;

@Service
public class DetalleOrdenDeCompraServiceImpl implements DetalleOrdenDeCompraService{

    @Autowired
    private DetalleOrdenDeCompraRepository detalleOrdenDeCompraRepository;
    @Autowired
    private OrdenDeCompraRepository ordenDeCompraRepository;

    public Page<DetalleOrdenDeCompra> getDetallesOrdenDeCompra(PageRequest pageRequest) {
        return detalleOrdenDeCompraRepository.findAll(pageRequest);
    }

    public Optional<DetalleOrdenDeCompra> getDetallesOrdenDeCompraById(Long id) {
        return detalleOrdenDeCompraRepository.findById(id);
    }

    public DetalleOrdenDeCompra createDetalleOrdenDeCompra(int cantidad, double precioUnitario, Producto producto, OrdenDeCompra ordenDeCompra) throws CategoriaDuplicadaException{
        DetalleOrdenDeCompra detalle = new DetalleOrdenDeCompra(cantidad, precioUnitario, producto, ordenDeCompra);
        DetalleOrdenDeCompra guardado = detalleOrdenDeCompraRepository.save(detalle);

        if (ordenDeCompra.getDetalleOrdenDeCompra() != null) {
            ordenDeCompra.getDetalleOrdenDeCompra().add(guardado);
        }
        ordenDeCompra = ordenDeCompraRepository.findById(ordenDeCompra.getId()).orElseThrow();

        return guardado;
    }

    public void deleteDetalleOrdenDeCompra(Long id) {
        detalleOrdenDeCompraRepository.deleteById(id);
    }
    
}
