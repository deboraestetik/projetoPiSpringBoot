package com.projetopi.tlgne.repositories;


import com.projetopi.tlgne.entities.Imagem;
import com.projetopi.tlgne.entities.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImagemRepository extends JpaRepository<Imagem, Long> {
}
