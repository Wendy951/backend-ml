package com.wlmc.mercaditolibre.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wlmc.mercaditolibre.modelo.ClienteEntity;
import com.wlmc.mercaditolibre.modelo.DetalleVentaEntity;
import com.wlmc.mercaditolibre.modelo.ProductoEntity;
import com.wlmc.mercaditolibre.modelo.VentaEntity;
import com.wlmc.mercaditolibre.repository.VentaRepository;
import com.wlmc.mercaditolibre.repository.clienteRepository;
import com.wlmc.mercaditolibre.repository.productoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VentaService {
    private final VentaRepository repository;
    private final productoRepository productoRepository;
    private final clienteRepository clienteRepository;


    //metodo para procesasr venta
    @Transactional
    public VentaEntity procesarVenta(VentaEntity ventaRequest, String email){
        ClienteEntity cliente = clienteRepository.findByEmail(email)
        .orElseThrow(()-> new RuntimeException("Cliente no registrado: " + email));

        ventaRequest.setCliente(cliente);
        ventaRequest.setFecha(LocalDateTime.now());
        ventaRequest.setEstadoPago("PENDIENTE");

        double total = 0.0;
        for (DetalleVentaEntity detalle : ventaRequest.getDetalles()) {
            ProductoEntity producto = 
            productoRepository.findById(detalle.getProducto().getId())
            .orElseThrow(()->new RuntimeException("producto no existe"));

            if(producto.getStock() < detalle.getCantidad()){
                throw new RuntimeException("Stock insuficiente del producto");
            }

            producto.setStock(producto.getStock() - detalle.getCantidad());
            productoRepository.save(producto);

            detalle.setPrecioUnitario(producto.getPrecio());
            detalle.setSubTotal(producto.getPrecio() * detalle.getCantidad());
            detalle.setVenta(ventaRequest);

            total += detalle.getSubTotal();
        }
        ventaRequest.setTotal(total);
        return repository.save(ventaRequest);
    }

    //metodo para procesar pago
    @Transactional public VentaEntity confirmarPago(Long idVenta){
        VentaEntity venta = repository.findById(idVenta)
        .orElseThrow(() -> new RuntimeException("Venta no encomtrada con ID: "
            +idVenta));
            venta.setEstadoPago("PAGADO");
            return repository.save(venta);
    }
    
//Buscar todos los productos
@Transactional(readOnly = true)
public List<VentaEntity> obtenerTodos(){
    return repository.findAll();}

//Buscar por Id
    @Transactional(readOnly = true)
    public VentaEntity ObtenerPorId(Long id) {
        return repository.findById(id).orElseThrow
        (() -> new RuntimeException(" Venta no encontrada"+id));
    }
//Guardad un registro
    @Transactional
    public VentaEntity guardarVenta(VentaEntity venta){
        return repository.save(venta);
        //Aqui pueden ir todas las validaciones
    }
//Eliminar una venta (solo el cliente dueño, solo si esta PENDIENTE, y devolviendo el stock)
     @Transactional
    public void eliminarVenta(Long id, String email) {
        VentaEntity venta = repository.findById(id)
        .orElseThrow(() -> new RuntimeException("No se puede eliminar"));

        if(venta.getCliente() == null || !venta.getCliente().getEmail().equals(email)){
            throw new RuntimeException("No puedes cancelar una compra que no es tuya");
        }

        if(!"PENDIENTE".equals(venta.getEstadoPago())){
            throw new RuntimeException("Solo se pueden cancelar compras en estado PENDIENTE");
        }

        for (DetalleVentaEntity detalle : venta.getDetalles()) {
            ProductoEntity producto = detalle.getProducto();
            if (producto != null) {
                producto.setStock(producto.getStock() + detalle.getCantidad());
                productoRepository.save(producto);
            }
        }

        repository.deleteById(id);
    }

    //Actualizar venta
    @Transactional
    public VentaEntity actualizarVenta(Long id, VentaEntity ventaEntity){
        VentaEntity ventaExistente = repository.findById(id)
        .orElseThrow(()-> new RuntimeException("Venta no existente !"));


        BeanUtils.copyProperties(ventaEntity, ventaExistente, "id");
        return repository.save (ventaExistente);
    }

    //obtener ventas por cliente
    @Transactional
    public List<VentaEntity> obtenerVentasPorCliente(String email){
        return repository.findByClienteEmail(email);
    }
}
