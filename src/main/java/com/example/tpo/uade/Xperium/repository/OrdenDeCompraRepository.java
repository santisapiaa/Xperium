package com.example.tpo.uade.Xperium.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.tpo.uade.Xperium.entity.OrdenDeCompra;

public interface OrdenDeCompraRepository extends JpaRepository<OrdenDeCompra, Long>{

    @Query(value = "select o from OrdenDeCompra o where o.id = ?1 ")
    Optional findById(Long id);

    @Query(value = "select o from OrdenDeCompra o where o.fecha = ?1")
    List<OrdenDeCompra> findByFecha(LocalDate fecha);

    @Query(value = "select o from OrdenDeCompra o where o.estado = ?1")
    List<OrdenDeCompra> findByEstado(String estado);
    
}
