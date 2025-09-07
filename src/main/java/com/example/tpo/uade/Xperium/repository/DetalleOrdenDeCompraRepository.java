package com.example.tpo.uade.Xperium.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.tpo.uade.Xperium.entity.DetalleOrdenDeCompra;

public interface DetalleOrdenDeCompraRepository extends JpaRepository<DetalleOrdenDeCompra, Long> {

    @Query(value = "select o from DetalleOrdenDeCompra o where o.id = ?1 ")
    List<DetalleOrdenDeCompra> findByOrdenCompraId(Long ordenCompraId);
    
}
