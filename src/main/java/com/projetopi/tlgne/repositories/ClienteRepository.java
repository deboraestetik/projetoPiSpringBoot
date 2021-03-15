package com.projetopi.tlgne.repositories;


import com.projetopi.tlgne.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository  extends JpaRepository<Cliente, Long> {

    @Query(value ="select * from cliente WHERE email = :usuario",nativeQuery = true)
    Cliente findAllUsuario(String usuario);
}
