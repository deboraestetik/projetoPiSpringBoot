package com.projetopi.tlgne.repositories;


import com.projetopi.tlgne.entities.Imagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImagemRepository extends JpaRepository<Imagem, Long> {

    Imagem findById(long id);

    @Query(value ="select * from imagem WHERE produto_id = :id order by imagem_principal DESC",nativeQuery = true)
    List<Imagem> findAllImagensByProdutoId(long id);

}
