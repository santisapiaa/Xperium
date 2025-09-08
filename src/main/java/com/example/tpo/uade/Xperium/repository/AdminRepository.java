package com.example.tpo.uade.Xperium.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.example.tpo.uade.Xperium.entity.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
    
    @Query(value = "select a from Admin a where a.id = ?1 ")
    Optional<Admin> findById(Long id);

    @Query(value = "select a from Admin a where a.nombre = ?1")
    List<Admin> findByNombre(String nombre);

    @Query(value = "select a from Admin a where a.email = ?1")
    Optional<Admin> findByEmail(String email);
    
}