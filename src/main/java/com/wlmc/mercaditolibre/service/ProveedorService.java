package com.wlmc.mercaditolibre.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.wlmc.mercaditolibre.modelo.ProveedorEntity;
import com.wlmc.mercaditolibre.repository.proveedorRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProveedorService {
    private final proveedorRepository repository;
    
//Buscar todos los productos
@Transactional(readOnly = true)
public List<ProveedorEntity> obtenerTodos(){
    return repository.findAll();}

//Buscar por Id
    @Transactional(readOnly = true)
    public ProveedorEntity obtenerPorId(Long id) {
        return repository.findById(id).orElseThrow
        (() -> new RuntimeException("Proveedor no encontrado"+id));
    }
//Guardad un registro
    @Transactional
    public ProveedorEntity guardadProveedor(ProveedorEntity proveedor){
        return repository.save(proveedor);
        //Aqui pueden ir todas las validaciones
    }
//Eliminar un producto
     @Transactional
    public void eliminarProveedor(Long id) {
        if(!repository.existsById(id)){
            throw new RuntimeException("No se puede eliminar");
        }   
        repository.deleteById (id);
    }

    //Actualizar producto
    @Transactional
    public ProveedorEntity actualizarProveedor(Long id, ProveedorEntity proveedorEntity){
        ProveedorEntity proveedorExistente = repository.findById(id)
        .orElseThrow(()-> new RuntimeException("Proveedor no existente !"));


        BeanUtils.copyProperties(proveedorEntity, proveedorExistente, "id");
        return repository.save (proveedorExistente);
    }


}

