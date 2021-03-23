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

    @Query(value = "SELECT * FROM `produto` where status = :true", nativeQuery = true)
    List<Produto> findAllProdutoHabilitado(@Param ("true") String habilitado);
    
    @Query(value="SELECT * FROM 'produto' where categoria = :cama", nativeQuery=true)
    List<Produto> findBed (@Param ("cama")String categoria);
    
     @Query(value="SELECT * FROM 'produto' where categoria = :mesa", nativeQuery=true)
    List<Produto> findTable (@Param ("mesa")String categoria);
    
    @Query(value="SELECT * FROM 'produto' where categoria = :banho", nativeQuery=true)
    List<Produto> findBath (@Param ("banho")String categoria);
}





