package com.projetopi.tlgne.repositories;


import com.projetopi.tlgne.entities.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    Produto findById(long id);

    @Query(value = "SELECT * FROM `produto` where nome LIKE %:seme%", nativeQuery = true)
    List<Produto> findAllSemelhanca(@Param ("seme") String semelhanca);
}





