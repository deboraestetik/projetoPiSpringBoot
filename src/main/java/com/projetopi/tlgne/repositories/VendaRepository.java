package com.projetopi.tlgne.repositories;

import com.projetopi.tlgne.entities.Venda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VendaRepository extends JpaRepository<Venda, Long> {

    @Query(value = "select * from venda WHERE cliente_id = :id", nativeQuery = true)
    Venda findVendaClienteById(@Param("id") Long id);

}
