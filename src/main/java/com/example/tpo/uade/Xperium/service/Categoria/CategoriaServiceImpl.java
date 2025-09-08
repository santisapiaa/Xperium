package com.example.tpo.uade.Xperium.service.Categoria;

import org.springframework.data.domain.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.example.tpo.uade.Xperium.entity.Categoria;
import com.example.tpo.uade.Xperium.exceptions.CategoriaDuplicadaException;
import com.example.tpo.uade.Xperium.repository.CategoriaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaServiceImpl  implements CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;
 
    // metodo para obtener todas las categorias con paginacion
    public Page<Categoria> getCategorias(PageRequest pageRequest) {
        return categoriaRepository.findAll(pageRequest);
    }

    // metodo para obtener una categoria por id
    public Optional<Categoria> getCategoriasById(Long id) {
       return categoriaRepository.findById(id);
    }
   
   // metodo para crear una categoria
    public Categoria createCategoria(String descripcion, String imagenUrl) throws CategoriaDuplicadaException {
        List<Categoria> categorias = categoriaRepository.findByDescription(descripcion);
        if (categorias.isEmpty()) {
            return categoriaRepository.save(new Categoria(descripcion, imagenUrl)); 
        }
        throw new CategoriaDuplicadaException(); 
    }

    // metodo para eliminar una categoria por id
    public void deleteCategoria(Long id) {
        categoriaRepository.deleteById(id);
    }

    // metodo para actualizar una categoria
    public Categoria updateCategoria(Long id, String descripcion, String imagenUrl) throws Exception {
        Categoria categoriaOpt = categoriaRepository.findById(id).orElseThrow(() -> new Exception("Categoria no encontrada"));
        categoriaOpt.setDescripcion(descripcion);
        categoriaOpt.setImagenUrl(imagenUrl);
        return categoriaRepository.save(categoriaOpt);
    }

}
