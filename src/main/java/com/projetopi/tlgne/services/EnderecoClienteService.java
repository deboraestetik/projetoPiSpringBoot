package com.projetopi.tlgne.services;


import com.projetopi.tlgne.entities.Cliente;
import com.projetopi.tlgne.entities.EnderecoCliente;
import com.projetopi.tlgne.repositories.ClienteRepository;
import com.projetopi.tlgne.repositories.EnderecoClienteRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnderecoClienteService {

    @Autowired
    private EnderecoClienteRepository enderecoClienteRepository;

    @Autowired
    private ClienteRepository clienteRepository;


    public EnderecoCliente findById(long id) {
        return enderecoClienteRepository.findById(id).orElse(null);
    }

    public List<EnderecoCliente> findAllEnderecoCliente(long id) {
        return enderecoClienteRepository.findAllEnderecoCliente(id);

    }

    public EnderecoCliente update(EnderecoCliente enderecoCliente, long id) throws NotFoundException {
        if (enderecoClienteRepository.existsById(enderecoCliente.getId())) {
            Cliente cliente = clienteRepository.findById(id).orElse(null);
            if(enderecoCliente.isPrincipal()){
                for(EnderecoCliente endereco: cliente.getEndereco()){
                    endereco.setPrincipal(false);
                    enderecoClienteRepository.save(endereco);
                }
            }
            enderecoCliente.setCliente(cliente);
            return enderecoClienteRepository.save(enderecoCliente);
        }
        throw new NotFoundException("Endereço não cadastrado");
    }

    public void delete(long id) {
        enderecoClienteRepository.deleteById(id);
    }

    public EnderecoCliente saveEnderecoCliente(EnderecoCliente enderecoCliente, long id) {
        Cliente cliente = clienteRepository.findById(id).orElseThrow(null);
        if(cliente.getEndereco().isEmpty()){
            enderecoCliente.setPrincipal(true);
        }
        cliente.setEndereco(enderecoCliente);
        enderecoCliente.setCliente(cliente);
        return enderecoClienteRepository.save(enderecoCliente);

    }
}
