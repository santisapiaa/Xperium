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
 
    public Page<Categoria> getCategorias(PageRequest pageRequest) {
        return categoriaRepository.findAll(pageRequest);
    }

    public Optional<Categoria> getCategoriasById(Long id) {
       return categoriaRepository.findById(id);
    }
   
    public Categoria createCategoria(String descripcion, String imagenUrl) throws CategoriaDuplicadaException {
        List<Categoria> categorias = categoriaRepository.findByDescription(descripcion);
        if (categorias.isEmpty()) {
            return categoriaRepository.save(new Categoria(descripcion, imagenUrl)); //Guardo y retorno la nueva categoria
        }
        throw new CategoriaDuplicadaException(); //Falta ver que hacer si ya existe la categoria
    }

    public void deleteCategoria(Long id) {
        categoriaRepository.deleteById(id);
    }

    //Falta implementar el metodo de actualizar categoria

}
