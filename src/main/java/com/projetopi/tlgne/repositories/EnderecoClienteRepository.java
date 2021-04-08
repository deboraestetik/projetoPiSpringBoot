package com.projetopi.tlgne.repositories;


import com.projetopi.tlgne.entities.EnderecoCliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnderecoClienteRepository extends JpaRepository<EnderecoCliente, Long> {

    @Query(value ="select * from endereco_cliente WHERE cliente_id = :id ",nativeQuery = true)
    List<EnderecoCliente> findAllEnderecoCliente(@Param("id")long id);
}
