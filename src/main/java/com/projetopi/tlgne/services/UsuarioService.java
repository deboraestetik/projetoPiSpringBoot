package com.projetopi.tlgne.services;


import com.projetopi.tlgne.entities.Funcionario;
import com.projetopi.tlgne.entities.MyUsuarioDetails;
import com.projetopi.tlgne.entities.Role;
import com.projetopi.tlgne.entities.Usuario;
import com.projetopi.tlgne.repositories.FuncionarioRepository;
import com.projetopi.tlgne.repositories.RoleRepository;
import com.projetopi.tlgne.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder encoder;

    public UsuarioService() {
    }

    @Override
    public UserDetails loadUserByUsername(String usarname) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByUsername(usarname)
                .orElseThrow(() -> new UsernameNotFoundException("Login não encontrado."));
        return new MyUsuarioDetails(usuario);

    }

    public Usuario saveUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public Usuario buscarUsuarioById(long id){
        return usuarioRepository.findById(id).orElse(null);
    }


    public Usuario verificarEmailExists(String email) {
        Usuario usuarioExists = usuarioRepository.findByUsername(email).orElse(null);
        return usuarioExists;
    }

    public void deleteById(long id){
        usuarioRepository.deleteById(id);
    }

    public Usuario findById(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }
}