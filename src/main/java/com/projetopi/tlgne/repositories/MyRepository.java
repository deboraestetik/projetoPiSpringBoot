package com.projetopi.tlgne.repositories;

import com.projetopi.tlgne.entities.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MyRepository extends JpaRepository<Object, Long> {

    @Query(value = "INSERT INTO `usuarios_roles`(`usuario_id`, `role_id`) VALUES (:usuario , :role)",nativeQuery = true)
    public void isertRoles(@Param("usuario") long usuario, @Param("role") long role);
}
