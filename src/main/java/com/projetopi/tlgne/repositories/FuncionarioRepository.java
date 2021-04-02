package com.projetopi.tlgne.repositories;


import com.projetopi.tlgne.entities.Cliente;
import com.projetopi.tlgne.entities.Funcionario;
import com.projetopi.tlgne.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

    @Query(value ="select * from funcionario WHERE nome = :nome",nativeQuery = true)
    Funcionario findByFuncionario(String nome);

    Optional<Usuario> findByEmail(String email);

}
