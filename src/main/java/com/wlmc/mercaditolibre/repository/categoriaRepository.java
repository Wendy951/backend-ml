package com.wlmc.mercaditolibre.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.wlmc.mercaditolibre.modelo.CategoriaEntity;


@Repository

public interface categoriaRepository extends JpaRepository<CategoriaEntity, Long>{

}
