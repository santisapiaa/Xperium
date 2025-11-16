package com.example.tpo.uade.Xperium.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.tpo.uade.Xperium.entity.Tarjeta;

@Repository
public interface TarjetaRepository extends JpaRepository<Tarjeta, Long> {
    Page<Tarjeta> findByCompradorId(Long compradorId, PageRequest pageRequest);
    
    Optional<Tarjeta> findByIdAndCompradorId(Long id, Long compradorId);
}
