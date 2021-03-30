package com.projetopi.tlgne.controllers;


import com.projetopi.tlgne.entities.Cliente;
import com.projetopi.tlgne.services.ClienteService;
import javassist.NotFoundException;
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
