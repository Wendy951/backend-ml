package com.wlmc.mercaditolibre.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wlmc.mercaditolibre.modelo.ProductoEntity;

@Repository
public interface productoRepository extends JpaRepository<ProductoEntity, Long> {
    

}
