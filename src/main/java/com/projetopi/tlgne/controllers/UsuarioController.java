package com.projetopi.tlgne.controllers;


import com.projetopi.tlgne.entities.Usuario;
import com.projetopi.tlgne.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

//    @Autowired
//    private BCryptPasswordEncoder encoder;

    @GetMapping("")
    public Usuario verificarEmailExists(@RequestHeader("email") String email){
        return usuarioService.verificarEmailExists(email);
    }

    @PostMapping("")
    public HttpStatus saveUsuario(@RequestBody Usuario usuario){
      //  usuario.setPassword(encoder.encode(usuario.getPassword()));
       return usuarioService.saveUsuario(usuario);
    }
}
