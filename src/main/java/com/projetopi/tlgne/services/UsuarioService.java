package com.projetopi.tlgne.services;


import com.projetopi.tlgne.entities.Usuario;
import com.projetopi.tlgne.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;


    @Override
    public UserDetails loadUserByUsername(String usarname) throws UsernameNotFoundException {

        Usuario usuario = usuarioRepository.findByUsername(usarname)
                .orElseThrow(() -> new UsernameNotFoundException("Login não encontrado."));

        return User.builder()
                .username(usuario.getUsername())
                .password(usuario.getPassword())
                .roles(usuario.getRoles())
                .build();
    }


    public UsuarioService() {
    }

    public void saveUsuario(Usuario usuario){
        usuarioRepository.save(usuario);
    }
}
