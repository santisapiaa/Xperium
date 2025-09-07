package com.example.tpo.uade.Xperium.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.tpo.uade.Xperium.entity.Direccion;

@Repository
public interface DireccionRepository extends JpaRepository<Direccion, Long> {

    @Query(value = "select d from Direccion d where d.id = ?1 and d.comprador.id = ?2")
    Optional<Direccion> findByIdAndCompradorId(Long id, Long compradorId);

    @Query(value = "select d from Direccion d where d.comprador.id = ?1")
    Page<Direccion> findByCompradorId(Long compradorId, PageRequest pageRequest);
}
