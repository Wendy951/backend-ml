package com.wlmc.mercaditolibre.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.wlmc.mercaditolibre.modelo.DetalleVentaEntity;
import com.wlmc.mercaditolibre.service.DetalleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequestMapping("/api/v1/detalle_venta")//mapero general de productos
@CrossOrigin(origins = "http://localhost:5173")//permiso a reacti
@RequiredArgsConstructor
public class DetalleController {
    private final DetalleService servicio;
    //endopoit ver todos lo productos
    @GetMapping("/")
    public ResponseEntity<List<DetalleVentaEntity>> listar() {
        return ResponseEntity.ok(servicio.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalleVentaEntity> obtenerDettalles(@PathVariable Long id) {
        return ResponseEntity.ok(servicio.obtenerPorId(id));//200 ok
    }
    
    //eliminar por id 
    @DeleteMapping("/{id}")
    public ResponseEntity<DetalleVentaEntity> eliminar(@PathVariable Long id) {
    servicio.eliminarDetalle(id);
    return ResponseEntity.noContent().build();//204 no content
}
//Agregar
@PostMapping("/")
public ResponseEntity<DetalleVentaEntity> crear(@RequestBody DetalleVentaEntity detalle) {
    DetalleVentaEntity nuevo = servicio.guardadDetalle(detalle);
    return new ResponseEntity<>(nuevo, HttpStatus.CREATED);//201 created
}

//actualizar 
@PutMapping("/{id}")
public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody DetalleVentaEntity detalle){
  try{
    DetalleVentaEntity detalleAct = servicio.actualizarDetalle(id,detalle);
  return ResponseEntity.ok(detalleAct);  
  }catch(RuntimeException e){
    return ResponseEntity.status(404).body(e.getMessage());
  }
}

}



