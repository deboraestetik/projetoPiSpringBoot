package com.projetopi.tlgne.controllers;


import com.projetopi.tlgne.entities.Cliente;
import com.projetopi.tlgne.entities.Funcionario;
import com.projetopi.tlgne.services.ClienteService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping("")
    public List<Cliente> findAll(){
        return clienteService.findAll();
    }

    @GetMapping("/find/{id}")
    public Cliente findClienteByUsuario(@PathVariable(value = "id") long id){
        return clienteService.findByCliente(id);
    }

    @GetMapping("/cpf")
    public Cliente verificarCpfJaCadastrado(@RequestHeader("cpf") String cpf){
        return clienteService.verificarCpfJaCadastrado(cpf);
    }
    @CrossOrigin
    @PostMapping(value = "")
    public Cliente saveCliente(@RequestBody Cliente cliente) {
        return clienteService.saveCliente(cliente);
    }

    @CrossOrigin
    @PutMapping("")
    public Cliente updateCliente(@RequestBody Cliente cliente) throws NotFoundException {
        return clienteService.saveUpdateCliente(cliente);
    }

    @CrossOrigin
    @DeleteMapping("/{id}")
    public void deleteCliente(@PathVariable(value = "id") long id) {
       clienteService.deleteById(id);
    }
    
}
