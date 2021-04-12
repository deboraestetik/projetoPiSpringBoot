package com.projetopi.tlgne.repositories;


import com.projetopi.tlgne.entities.Cliente;
import com.projetopi.tlgne.entities.DetalhesVenda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetalhesVendaRepository extends JpaRepository<DetalhesVenda, Long> {


}
