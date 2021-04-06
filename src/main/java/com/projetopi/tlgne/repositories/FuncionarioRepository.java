package com.projetopi.tlgne.repositories;


import com.projetopi.tlgne.entities.Cliente;
import com.projetopi.tlgne.entities.Funcionario;
import com.projetopi.tlgne.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

    @Query(value ="select * from funcionario WHERE nome = :nome",nativeQuery = true)
    Funcionario findByNome(@Param("nome")String nome);

    @Query(value ="select * from funcionario WHERE email = :username",nativeQuery = true)
    Funcionario findByEmail(@Param("username")String username);

    @Query(value ="select * from funcionario ORDER BY id DESC",nativeQuery = true)
    List<Funcionario> findAllFuncionarios();

    Optional<Funcionario> findByCpf(String cpf);

}
