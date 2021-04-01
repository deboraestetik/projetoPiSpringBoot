package com.projetopi.tlgne.services;


import com.projetopi.tlgne.entities.EnderecoFuncionario;
import com.projetopi.tlgne.entities.Funcionario;
import com.projetopi.tlgne.entities.Role;
import com.projetopi.tlgne.entities.Usuario;
import com.projetopi.tlgne.repositories.EnderecoFuncionarioRepository;
import com.projetopi.tlgne.repositories.FuncionarioRepository;
import com.projetopi.tlgne.repositories.RoleRepository;
import com.projetopi.tlgne.repositories.UsuarioRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class FuncionarioService {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private EnderecoFuncionarioRepository enderecoRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RoleRepository roleRepository;


    public List<Funcionario> findAll() {
        return funcionarioRepository.findAll();
    }

    public Funcionario findByFuncionario(String funcionario) {
        return funcionarioRepository.findByFuncionario(funcionario);
    }

    public Funcionario saveFuncionario(Funcionario funcionario) {
        if (funcionario.getEndereco() != null) {
            EnderecoFuncionario enderecoFuncionario = funcionario.getEndereco();
            enderecoRepository.save(enderecoFuncionario);
        }
        Funcionario funcionarioRetornado = funcionarioRepository.save(funcionario);

        if (saveUsuarioAndUsuariosRoles(funcionario)) {
            return null;
        }

        return funcionarioRetornado;
    }

    private boolean saveUsuarioAndUsuariosRoles(Funcionario funcionario) {
        Set<Role> setRole = new HashSet<>();
        if (funcionario.getCargo().equals("Estoquista")) {
            Role role = roleRepository.findById(3).orElseThrow(() -> new NumberFormatException());
            setRole.add(role);
        } else if (funcionario.getCargo().equals("Cliente")) {
            Role role = roleRepository.findById(2).orElseThrow(() -> new NumberFormatException());
            setRole.add(role);
        } else if (funcionario.getCargo().equals("Admin")) {
            Role role = roleRepository.findById(1).orElseThrow(() -> new NumberFormatException());
            setRole.add(role);
        }
        Usuario usuario = new Usuario();
        usuario.setUsername(funcionario.getEmail());
        usuario.setPassword(funcionario.getSenha());
        usuario.setRoles(setRole);
        HttpStatus httpStatus = usuarioService.saveUsuario(usuario);

        if(httpStatus == HttpStatus.EXPECTATION_FAILED){
            return true;
        }
        return false;
    }

    public Funcionario saveUpdateFuncionario(Funcionario funcionario) throws NotFoundException {

        if (funcionarioRepository.existsById(funcionario.getId())) {
            if (enderecoRepository.existsById(funcionario.getEndereco().getId())) {
                enderecoRepository.save(funcionario.getEndereco());
            }
            return funcionarioRepository.save(funcionario);
        }
        throw new NotFoundException("Funcioanrio não cadastrado");
    }


    public void deleteById(long id) {
        Funcionario funcionario = funcionarioRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Não existe funcionário cadastrado"));
        if (funcionario != null) {
            enderecoRepository.deleteById(funcionario.getEndereco().getId());
            funcionarioRepository.deleteById(id);
        }
    }

    public Funcionario findById(long id) {
        return funcionarioRepository.findById(id)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Funcionário com id " + id + "não existe"));
    }
}
