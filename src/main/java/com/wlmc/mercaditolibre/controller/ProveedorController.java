package com.wlmc.mercaditolibre.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.wlmc.mercaditolibre.modelo.ProveedorEntity;
import com.wlmc.mercaditolibre.service.ProveedorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequestMapping("/api/v1/proveedores")//mapero general de productos
@CrossOrigin(origins = "https://localhost:5173")//permiso a reacti
@RequiredArgsConstructor
public class ProveedorController {
    private final ProveedorService servicio;
    //endopoit ver todos lo productos
    @GetMapping("/")
    public ResponseEntity<List<ProveedorEntity>> listar() {
        return ResponseEntity.ok(servicio.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProveedorEntity> obtenerDettalles(@PathVariable Long id) {
        return ResponseEntity.ok(servicio.obtenerPorId(id));//200 ok
    }
    
    //eliminar por id 
    @DeleteMapping("/{id}")
    public ResponseEntity<ProveedorEntity> eliminar(@PathVariable Long id) {
    servicio.eliminarProveedor(id);
    return ResponseEntity.noContent().build();//204 no content
}
//Agregar
@PostMapping("/")
public ResponseEntity<ProveedorEntity> crear(@RequestBody ProveedorEntity proveedor) {
    ProveedorEntity nuevo = servicio.guardadProveedor(proveedor);
    return new ResponseEntity<>(nuevo, HttpStatus.CREATED);//201 created
}

//actualizar 
@PutMapping("/{id}")
public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody ProveedorEntity proveedor){
  try{
    ProveedorEntity proveedorAct = servicio.actualizarProveedor(id,proveedor);
  return ResponseEntity.ok(proveedorAct);  
  }catch(RuntimeException e){
    return ResponseEntity.status(404).body(e.getMessage());
  }
}

}

