package com.projetopi.tlgne.repositories;

import com.projetopi.tlgne.entities.Venda;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VendaRepository extends JpaRepository<Venda, Long> {

    @Query(value = "select * from venda where cliente_id = :id", nativeQuery = true)
    Venda findVendaClienteById(@Param("id") Long id);

    @Query(value = "select * from venda where data_venda between :dataInicio and :dataFim", nativeQuery = true)
    List<Venda> findVendasPorPeriodo(@Param("dataInicio") String dataInicio, @Param("dataFim") String dataFim);

}
