package com.projetopi.tlgne.controllers;


import com.projetopi.tlgne.entities.Cliente;
import com.projetopi.tlgne.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping("/{usuario}")
    public Cliente findAll(@PathVariable(value = "usuario") String usuario){
        return clienteService.findAllUsuario(usuario);
    }


}
