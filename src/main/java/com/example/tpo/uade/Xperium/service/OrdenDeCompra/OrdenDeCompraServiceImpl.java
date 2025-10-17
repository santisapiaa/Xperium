package com.example.tpo.uade.Xperium.service.OrdenDeCompra;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.tpo.uade.Xperium.entity.Comprador;
import com.example.tpo.uade.Xperium.entity.OrdenDeCompra;
import com.example.tpo.uade.Xperium.entity.Producto;
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
    public OrdenDeCompra createOrdenDeCompra(LocalDate fecha, double total, String estado, Comprador comprador) throws CategoriaDuplicadaException {
        return ordenDeCompraRepository.save(new OrdenDeCompra(fecha, total, estado, comprador));
    }

    @Override
    public void deleteOrdenDeCompra(Long id) {
        ordenDeCompraRepository.deleteById(id);
    }

    @Override
    public OrdenDeCompra finalizeOrdenDeCompra(Long id) {
        // Buscar la orden por ID
        Optional<OrdenDeCompra> ordenOpt = ordenDeCompraRepository.findById(id);
        if (ordenOpt.isPresent()) {
            OrdenDeCompra orden = ordenOpt.get();
            // Verificar que la orden esté en estado PENDIENTE
            if ("PENDIENTE".equals(orden.getEstado())) {
                for (var detalle : orden.getDetalleOrdenDeCompra()) {
                    Producto producto = detalle.getProducto();
                    if (producto.getStock() < detalle.getCantidad()) {
                        throw new RuntimeException("Stock insuficiente para el producto: " + producto.getNombre());
                    }
                    // Descontar el stock del producto
                    producto.setStock(producto.getStock() - detalle.getCantidad());
                }
                
                orden.setEstado("FINALIZADA");
                return ordenDeCompraRepository.save(orden);
            } else {
                throw new RuntimeException("La orden no está en estado PENDIENTE");
            }
        } else {
            throw new RuntimeException("Orden no encontrada");
        }
    }
}
