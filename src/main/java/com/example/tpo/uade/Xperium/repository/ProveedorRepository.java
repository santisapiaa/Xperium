package com.example.tpo.uade.Xperium.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.example.tpo.uade.Xperium.entity.Proveedor;

@Repository
public interface ProveedorRepository extends JpaRepository<Proveedor, Long> {
    
    @Query(value = "select p from Proveedor p where p.id = ?1 ")
    Optional<Proveedor> findById(Long id);

    @Query(value = "select p from Proveedor p where p.nombre = ?1")
    List<Proveedor> findByNombre(String nombre);

    @Query(value = "select p from Proveedor p where p.email = ?1")
    Optional<Proveedor> findByEmail(String email);
    
}
