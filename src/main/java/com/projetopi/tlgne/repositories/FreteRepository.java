package com.projetopi.tlgne.repositories;


import com.projetopi.tlgne.entities.Frete;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FreteRepository extends JpaRepository<Frete, Integer> {
}
