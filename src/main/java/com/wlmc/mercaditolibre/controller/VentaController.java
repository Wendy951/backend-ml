package com.wlmc.mercaditolibre.controller;

import java.security.Principal;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.wlmc.mercaditolibre.modelo.VentaEntity;
//import com.wlmc.mercaditolibre.service.ProcesarVenta;
import com.wlmc.mercaditolibre.service.VentaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/v1/ventas/")//mapero general de productos
@CrossOrigin(origins = "https://localhost:5173")//permiso a reacti
@RequiredArgsConstructor
public class VentaController {
    private final VentaService service;
 /*private final ProcesarVenta serpProcesarVenta;

@PostMapping("/")
public ResponseEntity<VentaEntity> crearVenta(@RequestBody VentaEntity venta) {
    return ResponseEntity.ok(serpProcesarVenta.ProcesarVenta(venta));
}*/
    public String postMethodName(@RequestBody String entity) {
        //TODO: process POST request
        return entity;
    }

      //endopoit ver todas las ventas 
    @GetMapping("/")
    public ResponseEntity<List<VentaEntity>> listar() {
        return ResponseEntity.ok(service.obtenerTodos());
    }

    @PostMapping("/")
public ResponseEntity<?> crearVenta(@RequestBody VentaEntity venta,
    Principal principal) {
        try{
            String email = principal.getName();
            VentaEntity nuevaVenta = service.procesarVenta(venta, email);
            return ResponseEntity.ok(nuevaVenta);
        }catch(Exception ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/mis-compras")
    public ResponseEntity<List<VentaEntity>>listarMisCompras(Principal principal) {
        String email = principal.getName();
        return ResponseEntity.ok(service.obtenerVentasPorCliente(email));
    }
    
    
   /*  //endopoit ver todas las ventas 
    @GetMapping("/")
    public ResponseEntity<List<VentaEntity>> listar() {
        return ResponseEntity.ok(servicio.obtenerTodos());
    }*/

    @GetMapping("/{id}")
    public ResponseEntity<VentaEntity> obtenerDettalles(@PathVariable Long id) {
        return ResponseEntity.ok(service.ObtenerPorId(id));//200 ok
    }
    
    //eliminar por id 
    @DeleteMapping("/{id}")
    public ResponseEntity<VentaEntity> eliminar(@PathVariable Long id) {
    service.eliminarVenta(id);
    return ResponseEntity.noContent().build();//204 no content
}
//Agregar
@PostMapping("/guardar")
public ResponseEntity<VentaEntity> crear(@RequestBody VentaEntity venta) {
    VentaEntity nuevo = service.guardarVenta(venta);
    return new ResponseEntity<>(nuevo, HttpStatus.CREATED);//201 created
}

//actualizar 
@PutMapping("/{id}")
public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody VentaEntity venta){
  try{
    VentaEntity ventaAct = service.actualizarVenta(id,venta);
  return ResponseEntity.ok(ventaAct);  
  }catch(RuntimeException e){
    return ResponseEntity.status(404).body(e.getMessage());
  }
}

}