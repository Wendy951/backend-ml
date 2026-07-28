package com.wlmc.mercaditolibre.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wlmc.mercaditolibre.modelo.DetalleVentaEntity;
import com.wlmc.mercaditolibre.repository.detalleVentaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DetalleService {
    private final detalleVentaRepository repository;
    
//Buscar todos los productos
@Transactional(readOnly = true)
public List<DetalleVentaEntity> obtenerTodos(){
    return repository.findAll();}

//Buscar por Id
    @Transactional(readOnly = true)
    public DetalleVentaEntity obtenerPorId(Long id) {
        return repository.findById(id).orElseThrow
        (() -> new RuntimeException("Detalle no encontrado"+id));
    }
//Guardad un registro
    @Transactional
    public DetalleVentaEntity guardadDetalle(DetalleVentaEntity detalle){
        return repository.save(detalle);
        //Aqui pueden ir todas las validaciones
    }
//Eliminar un producto
     @Transactional
    public void eliminarDetalle(Long id) {
        if(!repository.existsById(id)){
            throw new RuntimeException("No se puede eliminar");
        }   
        repository.deleteById (id);
    }

    //Actualizar producto
    @Transactional
    public DetalleVentaEntity actualizarDetalle(Long id, DetalleVentaEntity detalleVentaEntity){
        DetalleVentaEntity detalleExistente = repository.findById(id)
        .orElseThrow(()-> new RuntimeException("Detalle no existente !"));


        BeanUtils.copyProperties(detalleVentaEntity, detalleExistente, "id");
        return repository.save (detalleExistente);
    }

}


