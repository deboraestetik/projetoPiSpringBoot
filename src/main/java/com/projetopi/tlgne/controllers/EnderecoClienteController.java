package com.projetopi.tlgne.controllers;


import com.projetopi.tlgne.entities.EnderecoCliente;
import com.projetopi.tlgne.services.EnderecoClienteService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/enderecos")
public class EnderecoClienteController {

    @Autowired
    private EnderecoClienteService enderecoClienteService;

    @GetMapping("/{id}")
    public EnderecoCliente findById(@PathVariable(value = "id") long id) {
        return enderecoClienteService.findById(id);
    }

    @GetMapping("/cliente/{id}")
    public List<EnderecoCliente> findAllEnderecoCliente(@PathVariable (value = "id") long id) throws IOException {
        return enderecoClienteService.findAllEnderecoCliente(id);
    }

    @PostMapping("/cliente/{id}")
    public EnderecoCliente saveEnderecoCliente(@RequestBody EnderecoCliente enderecoCliente, @PathVariable(value = "id") long id) {
        return enderecoClienteService.saveEnderecoCliente(enderecoCliente,id);
    }

    @PutMapping("")
    public EnderecoCliente updateEndereco(@RequestBody EnderecoCliente enderecoCliente, @RequestHeader("id") long id) throws NotFoundException {
        return enderecoClienteService.update(enderecoCliente,id);
    }

    @DeleteMapping("/{id}")
    public void deleteEndereco(@PathVariable(value = "id") long id) {
        enderecoClienteService.delete(id);
    }

}


