package com.projetopi.tlgne.services;

import com.projetopi.tlgne.entities.Cliente;
import com.projetopi.tlgne.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public ClienteService() {
    }

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public Cliente findAllUsuario(String usuario) {
        return clienteRepository.findAllUsuario(usuario);
    }
}
