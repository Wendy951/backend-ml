package com.wlmc.mercaditolibre.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.wlmc.mercaditolibre.modelo.ProductoEntity;
import com.wlmc.mercaditolibre.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequestMapping("/api/v1/productos")//mapero general de productos
@CrossOrigin(origins = "https://localhost:5173")//permiso a reacti
@RequiredArgsConstructor
public class ProductoController {
    private final ProductoService servicio;
    //endopoit ver todos lo productos
    @GetMapping("/")
    public ResponseEntity<List<ProductoEntity>> listar() {
        return ResponseEntity.ok(servicio.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoEntity> obtenerDettalles(@PathVariable Long id) {
        return ResponseEntity.ok(servicio.ObtenerPorId(id));//200 ok
    }
    
    //eliminar por id 
    @DeleteMapping("/{id}")
    public ResponseEntity<ProductoEntity> eliminar(@PathVariable Long id) {
    servicio.eliminarProducto(id);
    return ResponseEntity.noContent().build();//204 no content
}
//Agregar
@PostMapping("/")
public ResponseEntity<ProductoEntity> crear(@RequestBody ProductoEntity producto) {
    ProductoEntity nuevo = servicio.guardadProducto(producto);
    return new ResponseEntity<>(nuevo, HttpStatus.CREATED);//201 created
}

//actualizar 
@PutMapping("/{id}")
public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody ProductoEntity producto){
  try{
    ProductoEntity productoAct = servicio.actualizarProducto(id,producto);
  return ResponseEntity.ok(productoAct);  
  }catch(RuntimeException e){
    return ResponseEntity.status(404).body(e.getMessage());
  }
}

}
