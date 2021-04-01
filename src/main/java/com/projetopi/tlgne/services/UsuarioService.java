package com.projetopi.tlgne.services;


import com.projetopi.tlgne.entities.MyUsuarioDetails;
import com.projetopi.tlgne.entities.Usuario;
import com.projetopi.tlgne.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public UsuarioService() {
    }

    @Override
    public UserDetails loadUserByUsername(String usarname) throws UsernameNotFoundException {

        Usuario usuario = usuarioRepository.findByUsername(usarname)
                .orElseThrow(() -> new UsernameNotFoundException("Login não encontrado."));

        return new MyUsuarioDetails(usuario);
    }


    public HttpStatus saveUsuario(Usuario usuario) {
        Optional<Usuario> usuarioExists = usuarioRepository.findByUsername(usuario.getUsername());
        if (usuarioExists.isPresent()) {
            return HttpStatus.EXPECTATION_FAILED;
        } else {
            usuarioRepository.save(usuario);
            return HttpStatus.CREATED;
        }
    }

    public Usuario verificarEmailExists(String email) {
        Usuario usuarioExists = usuarioRepository.findByUsername(email).orElse(null);
        return usuarioExists;
    }
}