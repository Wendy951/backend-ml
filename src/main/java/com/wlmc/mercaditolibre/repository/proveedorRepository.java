package com.wlmc.mercaditolibre.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.wlmc.mercaditolibre.modelo.ProveedorEntity;

@Repository
public interface proveedorRepository extends JpaRepository<ProveedorEntity, Long>{

}
