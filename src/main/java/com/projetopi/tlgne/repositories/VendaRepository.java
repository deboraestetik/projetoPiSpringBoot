package com.projetopi.tlgne.repositories;

import com.projetopi.tlgne.entities.Venda;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VendaRepository extends JpaRepository<Venda, Long> {

    @Query(value = "select * from venda where cliente_id = :id ORDER BY  data_venda DESC", nativeQuery = true)
    List<Venda> findVendaClienteById(@Param("id") Long id);

    @Query(value = "select * from venda where data_venda between :dataInicio and :dataFim", nativeQuery = true)
    List<Venda> findVendasPorPeriodo(@Param("dataInicio") Date dataInicio, @Param("dataFim") Date dataFim);

    @Query(value = "select * from venda where cliente_id = :id and numero_pedido = :numeroPedido ", nativeQuery = true)
    List<Venda> findVendaByNumeroPedido(@Param("id") Long id, @Param("numeroPedido") String numeroPedido);
    
    @Query(value="select * from venda inner join detalhes_venda on detalhes_venda.venda_id = venda.id inner join frete on frete.id = venda.frete_id;", nativeQuery = true)
    List<Venda> findVendas ();
}
