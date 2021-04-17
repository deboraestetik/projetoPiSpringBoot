package com.projetopi.tlgne.repositories;

import com.projetopi.tlgne.entities.DetalhesVenda;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DetalhesVendaRepository extends JpaRepository<DetalhesVenda, Long> {
    
    @Query(value = "select * from detalhes_venda where venda_id = :id", nativeQuery = true)
    List<DetalhesVenda> findVendaById(@Param("id") Long id);

}
