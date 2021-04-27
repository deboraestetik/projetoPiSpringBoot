package com.projetopi.tlgne.repositories;


import com.projetopi.tlgne.entities.EnderecoCliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnderecoClienteRepository extends JpaRepository<EnderecoCliente, Long> {

    @Query(value ="select * from endereco_cliente WHERE cliente_id = :id and endereco_cobranca = 0 ORDER BY status DESC",nativeQuery = true)
    List<EnderecoCliente> findAllEnderecoCliente(@Param("id")long id);

    @Query(value ="select * from endereco_cliente WHERE cliente_id = :id and endereco_cobranca = 0 and status = 1",nativeQuery = true)
    List<EnderecoCliente> findAllEnderecoClienteAtivos(long id);
}
