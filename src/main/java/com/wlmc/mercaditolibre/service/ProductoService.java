package com.wlmc.mercaditolibre.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.wlmc.mercaditolibre.modelo.ProductoEntity;
import com.wlmc.mercaditolibre.repository.productoRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductoService {
    private final productoRepository repository;
    
//Buscar todos los productos
@Transactional(readOnly = true)
public List<ProductoEntity> obtenerTodos(){
    return repository.findAll();}

//Buscar por Id
    @Transactional(readOnly = true)
    public ProductoEntity ObtenerPorId(Long id) {
        return repository.findById(id).orElseThrow
        (() -> new RuntimeException("Producto no encontrado"+id));
    }
//Guardad un registro
    @Transactional
    public ProductoEntity guardadProducto(ProductoEntity producto){
        return repository.save(producto);
        //Aqui pueden ir todas las validaciones
    }
//Eliminar un producto
     @Transactional
    public void eliminarProducto(Long id) {
        if(!repository.existsById(id)){
            throw new RuntimeException("No se puede eliminar");
        }   
        repository.deleteById (id);
    }

    //Actualizar producto
    @Transactional
    public ProductoEntity actualizarProducto(Long id, ProductoEntity detallePoProductoEntity){
        ProductoEntity productoExistente = repository.findById(id)
        .orElseThrow(()-> new RuntimeException("Producto no existente !"));


        BeanUtils.copyProperties(detallePoProductoEntity, productoExistente, "id");
        return repository.save (productoExistente);
    }


}
