package com.wlmc.mercaditolibre.service;

import org.springframework.stereotype.Service;

import com.wlmc.mercaditolibre.modelo.DetalleVentaEntity;
import com.wlmc.mercaditolibre.modelo.ProductoEntity;
import com.wlmc.mercaditolibre.modelo.VentaEntity;
import com.wlmc.mercaditolibre.repository.VentaRepository;
import com.wlmc.mercaditolibre.repository.productoRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProcesarVenta {
    private final VentaRepository ventarepo;
    private final productoRepository prodrepo;

    @Transactional
    public VentaEntity ProcesarVenta(VentaEntity ventaRequest){
    ventaRequest.setFecha(java.time.LocalDateTime.now());
    ventaRequest.setEstadoPago("PENDIENTE");

    //Calcualr totales y descontar el stock
    Double total = 0.0;
    for(DetalleVentaEntity detalle : ventaRequest.getDetalles()) {
        ProductoEntity p =prodrepo.findById(detalle.getProducto().getId()).orElseThrow();
        p.setStock(p.getStock() - detalle.getCantidad());//Actualizar

        detalle.setPrecioUnitario(p.getPrecio());
        detalle.setSubTotal(p.getPrecio()*detalle.getCantidad());
        detalle.setVenta(ventaRequest);
        total += detalle.getSubTotal();
    }
    ventaRequest.setTotal(total);
    return ventarepo.save(ventaRequest);

    }
}