package com.projetopi.tlgne.services;


import com.projetopi.tlgne.entities.Funcionario;
import com.projetopi.tlgne.entities.MyUsuarioDetails;
import com.projetopi.tlgne.entities.Usuario;
import com.projetopi.tlgne.repositories.FuncionarioRepository;
import com.projetopi.tlgne.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.util.Optional;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    public UsuarioService() {
    }

    @Override
    public UserDetails loadUserByUsername(String usarname) throws UsernameNotFoundException {
//        Funcionario funcionario = funcionarioRepository.findByFuncionario(usarname);
//        if (funcionario.isStatus()) {
            Usuario usuario = usuarioRepository.findByUsername(usarname)
                    .orElseThrow(() -> new UsernameNotFoundException("Login não encontrado."));
            return new MyUsuarioDetails(usuario);
//        }
//        return null;

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
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        if (result == true && usuarioExists == null) {
            return null;
        } else if (result == false) {
            System.out.println("Email não valido");
        }
        return usuarioExists;
    }
}