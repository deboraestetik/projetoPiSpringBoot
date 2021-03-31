package com.projetopi.tlgne.repositories;


import com.projetopi.tlgne.entities.EnderecoFuncionario;
import com.projetopi.tlgne.entities.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecoFuncionarioRepository extends JpaRepository<EnderecoFuncionario, Long> {
}
