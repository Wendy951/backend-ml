package com.wlmc.mercaditolibre.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wlmc.mercaditolibre.modelo.ClienteEntity;


@Repository
public interface clienteRepository extends JpaRepository<ClienteEntity, Long> {
    Optional<ClienteEntity> findByEmail(String email);

}
