package com.projetopi.tlgne.controllers;


import com.projetopi.tlgne.entities.Usuario;
import com.projetopi.tlgne.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("")
    public void saveUsuario(@RequestBody Usuario usuario){
        usuarioService.saveUsuario(usuario);
    }
}
