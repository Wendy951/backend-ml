package com.wlmc.mercaditolibre.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wlmc.mercaditolibre.modelo.DetalleVentaEntity;

@Repository
public interface detalleVentaRepository extends JpaRepository<DetalleVentaEntity, Long> {

}
