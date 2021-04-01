package com.projetopi.tlgne.config;


import com.projetopi.tlgne.entities.Role;
import com.projetopi.tlgne.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class RolesConfig {

    @Autowired
    private RoleRepository roleRepository;

    @Bean
    public void saveRoles() {
        Role role1 = new Role();
        role1.setId(1);
        role1.setName("ROLE_ADMIN");
        roleRepository.save(role1);
        Role role2 = new Role();
        role2.setId(2);
        role2.setName("ROLE_CLIENTE");
        roleRepository.save(role2);
        Role role3 = new Role();
        role3.setId(3);
        role3.setName("ROLE_ESTOQUISTA");
        roleRepository.save(role3);
        Role role4 = new Role();
        role4.setId(4);
        role4.setName("ROLE_USERS");
        roleRepository.save(role4);
    }
}
