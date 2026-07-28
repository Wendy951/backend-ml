package com.wlmc.mercaditolibre.modelo;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "productos")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder
public class ProductoEntity {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

@Column(nullable = false, length = 100)
private String nombre;

@Column(length = 500)
private String descripcion;

@Column(nullable = false)
private Double precio;

@Column(nullable = false)
private Integer stock;

private String imagenUrl;

//------ Relaciones de llaves FK ------
@ManyToOne (fetch = FetchType.EAGER)
@JoinColumn(name = "categoria_id")//llave foranea de categoria
private CategoriaEntity categoria;

@ManyToOne (fetch = FetchType.EAGER)
@JoinColumn(name = "proveedor_id")//llave foranea de proveedor
private ProveedorEntity proveedor;

@ManyToOne (fetch = FetchType.EAGER)
@JoinColumn(name = "cliente_id")//llave foranea de cliente
private ClienteEntity cliente;

@ManyToOne (fetch = FetchType.EAGER)
@JoinColumn(name = "venta_id")//llave foranea de proveedor venta
private DetalleVentaEntity venta;
}
