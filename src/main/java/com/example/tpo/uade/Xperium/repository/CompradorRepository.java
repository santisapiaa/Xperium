package com.example.tpo.uade.Xperium.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.tpo.uade.Xperium.entity.Comprador;

@Repository
public interface CompradorRepository extends JpaRepository<Comprador, Long>{

    @Query(value = "select c from Comprador c where c.id = ?1 ")
    Optional<Comprador> findById(Long id);

    @Query(value = "select c from Comprador c where c.email = ?1")
    List<Comprador> findByEmail(String email);
}