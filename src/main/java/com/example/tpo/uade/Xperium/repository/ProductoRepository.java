package com.example.tpo.uade.Xperium.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.example.tpo.uade.Xperium.entity.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    @Query(value = "select p from Producto p where p.id = ?1")
    Optional<Producto> findById(Long id);

    @Query(value = "select p from Producto p where p.nombre = ?1")
    List<Producto> findByNombre(String nombre);

    @Query(value = "select p from Producto p where p.categoria.id = ?1 and p.stock > 0")
    Page<Producto> findByCategoriaId(Long categoriaId, PageRequest pageRequest);

    @Query(value = "select p from Producto p where p.proveedor.id = ?1")
    Page<Producto> findByProveedorId(Long proveedorId, PageRequest pageRequest);

    @Query(value = "select p from Producto p where p.id = ?1 and p.proveedor.id = ?2")
    Optional<Producto> findByIdAndProveedorId(Long id, Long proveedorId);
    
    @Query(value = "select p from Producto p where p.stock > 0")
    Page<Producto> findAllWithStock(PageRequest pageRequest);
}
