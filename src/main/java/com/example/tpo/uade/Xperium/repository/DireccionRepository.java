package com.example.tpo.uade.Xperium.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.tpo.uade.Xperium.entity.Direccion;

@Repository
public interface DireccionRepository extends JpaRepository<Direccion, Long>  {

    @Query(value = "select d from Direccion d where d.id = ?1 ")
    Optional<Direccion> findById(Long id);

    @Query(value = "select d from Direccion d where d.comprador.id = ?1")
    List<Direccion> findByCompradorId(Long compradorId);
    
}
