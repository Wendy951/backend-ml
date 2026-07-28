package com.wlmc.mercaditolibre.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.wlmc.mercaditolibre.modelo.ClienteEntity;
import com.wlmc.mercaditolibre.repository.clienteRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClienteService {
    private final clienteRepository repository;
    
//Buscar todos los productos
@Transactional(readOnly = true)
public List<ClienteEntity> obtenerTodos(){
    return repository.findAll();}

//Buscar por Id
    @Transactional(readOnly = true)
    public ClienteEntity ObtenerPorId(Long id) {
        return repository.findById(id).orElseThrow
        (() -> new RuntimeException("Cliente no encontrado"+id));
    }
//Guardad un registro
    @Transactional
    public ClienteEntity guardadCliente(ClienteEntity cliente){
        return repository.save(cliente);
        //Aqui pueden ir todas las validaciones
    }
//Eliminar un producto
     @Transactional
    public void eliminarCliente(Long id) {
        if(!repository.existsById(id)){
            throw new RuntimeException("No se puede eliminar");
        }   
        repository.deleteById (id);
    }

    //Actualizar producto
    @Transactional
    public ClienteEntity actualizarCliente(Long id, ClienteEntity detalleCliente){
        ClienteEntity clienteExistente = repository.findById(id)
        .orElseThrow(()-> new RuntimeException("Cliente no existente !"));


        BeanUtils.copyProperties(detalleCliente, clienteExistente, "id");
        return repository.save (clienteExistente);
    }


}


