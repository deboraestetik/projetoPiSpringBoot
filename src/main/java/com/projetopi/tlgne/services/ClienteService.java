package com.projetopi.tlgne.services;

import com.projetopi.tlgne.entities.Cliente;
import com.projetopi.tlgne.repositories.ClienteRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public ClienteService() {
    }

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public List<Cliente> findAll(){
        return clienteRepository.findAll();
    }

    public Cliente findAllUsuario(String usuario) {
        return clienteRepository.findAllUsuario(usuario);
    }

    
    public Cliente saveCliente (Cliente cliente){
        return clienteRepository.save(cliente);
        
    }
    
    public Cliente saveUpdateCliente(Cliente cliente) throws NotFoundException {

        if (clienteRepository.existsById(cliente.getId())) {
            return clienteRepository.save(cliente);
        }
        throw new NotFoundException("Cliente não cadastrado");
    }

    public void deleteById(long id){
        clienteRepository.deleteById(id);
    }
}
