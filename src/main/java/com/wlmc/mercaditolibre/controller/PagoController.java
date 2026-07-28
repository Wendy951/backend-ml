package com.wlmc.mercaditolibre.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stripe.Stripe;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import com.wlmc.mercaditolibre.dto.PagoRequest;
import com.wlmc.mercaditolibre.modelo.VentaEntity;
import com.wlmc.mercaditolibre.service.VentaService;


@RestController
@RequestMapping("/api/v1/pagos")
public class PagoController {
    @Value("${stripe.apikey.secret}")
    private String stripeSecretKey;

    private final VentaService ventaService;

    public PagoController(VentaService ventaService) {
        this.ventaService = ventaService;
    }
    
    @PostMapping("/crear-intencion")
    public ResponseEntity<?> crearIntencion(@RequestBody PagoRequest peticion) {
        try {
            Stripe.apiKey = stripeSecretKey;
            VentaEntity venta = ventaService.ObtenerPorId(peticion.getIdVenta());
            long montoCentavos = (long) (venta.getTotal() * 100);

            PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                    .setAmount(montoCentavos)
                    .setCurrency(peticion.getMoneda() != null ? peticion.getMoneda() : "mxn")
                    .putMetadata("id_venta", venta.getId().toString())
                    .build();

            PaymentIntent intent = PaymentIntent.create(params);

            Map<String, String> respuesta = new HashMap<>();
            respuesta.put("clientSecret", intent.getClientSecret());

            return ResponseEntity.ok(respuesta);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PostMapping("/confirmar-pago/{idVenta}")
    public ResponseEntity<?> confirmarPago(@PathVariable Long idVenta) {
        try {
            VentaEntity ventaActualizada = ventaService.confirmarPago(idVenta);
            return ResponseEntity.ok(ventaActualizada);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

}
