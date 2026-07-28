package com.wlmc.mercaditolibre.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wlmc.mercaditolibre.modelo.VentaEntity;

@Repository
public interface VentaRepository extends JpaRepository<VentaEntity, Long>{

    List<VentaEntity> findByClienteEmail(String email);

}