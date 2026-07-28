package com.wlmc.mercaditolibre.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.wlmc.mercaditolibre.modelo.CategoriaEntity;
import com.wlmc.mercaditolibre.repository.categoriaRepository;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class CategoriaService {
    private final categoriaRepository repository;
    
//Buscar todos los productos
@Transactional(readOnly = true)
public List<CategoriaEntity> obtenerTodos(){
    return repository.findAll();}

//Buscar por Id
    @Transactional(readOnly = true)
    public CategoriaEntity ObtenerPorId(Long id) {
        return repository.findById(id).orElseThrow
        (() -> new RuntimeException("Categoria no encontrada"+id));
    }
//Guardad un registro
    @Transactional
    public CategoriaEntity guardarCategoria(CategoriaEntity categoria){
        return repository.save(categoria);
        //Aqui pueden ir todas las validaciones
    }
//Eliminar un producto
     @Transactional
    public void eliminarCategoria(Long id) {
        if(!repository.existsById(id)){
            throw new RuntimeException("No se puede eliminar");
        }   
        repository.deleteById (id);
    }

    //Actualizar producto
    @Transactional
    public CategoriaEntity actualizarCategoria(Long id, CategoriaEntity detalleCategoriaEntity){
        CategoriaEntity categoriaExistente = repository.findById(id)
        .orElseThrow(()-> new RuntimeException("Categoria no existente !"));


        BeanUtils.copyProperties(detalleCategoriaEntity, categoriaExistente, "id");
        return repository.save (categoriaExistente);
    }


}