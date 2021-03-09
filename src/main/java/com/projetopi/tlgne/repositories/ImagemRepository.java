package com.projetopi.tlgne.repositories;


import com.projetopi.tlgne.entities.Imagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImagemRepository extends JpaRepository<Imagem, Long> {

    Imagem findById(long id);

//    @Query(value ="selectselect * from imagem WHERE produto_id = id;")
//    List<Imagem> findAll(long id);
}
