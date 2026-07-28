package com.wlmc.mercaditolibre.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.wlmc.mercaditolibre.modelo.ClienteEntity;
import com.wlmc.mercaditolibre.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/api/v1/clientes")//mapero general de productos
@CrossOrigin(origins = "https://localhost:5173")//permiso a reacti
@RequiredArgsConstructor
public class ClienteController {
    private final ClienteService servicio;
    //endopoit ver todos lo productos
    @GetMapping("/")
    public ResponseEntity<List<ClienteEntity>> listar() {
        return ResponseEntity.ok(servicio.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteEntity> obtenerDettalles(@PathVariable Long id) {
        return ResponseEntity.ok(servicio.ObtenerPorId(id));//200 ok
    }
    
    //eliminar por id 
    @DeleteMapping("/{id}")
    public ResponseEntity<ClienteEntity> eliminar(@PathVariable Long id) {
    servicio.eliminarCliente(id);
    return ResponseEntity.noContent().build();//204 no content
}
//Agregar
@PostMapping("/")
public ResponseEntity<ClienteEntity> crear(@RequestBody ClienteEntity cliente) {
    ClienteEntity nuevo = servicio.guardadCliente(cliente);
    return new ResponseEntity<>(nuevo, HttpStatus.CREATED);//201 created
}

//actualizar 
@PutMapping("/{id}")
public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody ClienteEntity cliente){
  try{
    ClienteEntity ClienteAct = servicio.actualizarCliente(id,cliente);
  return ResponseEntity.ok(ClienteAct);  
  }catch(RuntimeException e){
    return ResponseEntity.status(404).body(e.getMessage());
  }
}

}

