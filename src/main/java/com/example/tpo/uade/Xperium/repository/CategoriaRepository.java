package com.example.tpo.uade.Xperium.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.tpo.uade.Xperium.entity.Categoria;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long>  {

    @Query(value = "select c from Categoria c where c.id = ?1 ")
    Optional findById(Long id);

    @Query(value = "select c from Categoria c where c.descripcion = ?1")
    List<Categoria> findByDescription(String descripcion);
     
}