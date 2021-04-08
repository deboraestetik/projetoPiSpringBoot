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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class FuncionarioService {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private EnderecoFuncionarioRepository enderecoFuncionarioRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Funcionario> findAll() {
        return funcionarioRepository.findAllFuncionarios();
    }

    public Funcionario findByNomeFuncionario(String nomeFuncionario) {
        return funcionarioRepository.findByNome(nomeFuncionario);
    }

    public Funcionario saveFuncionario(Funcionario funcionario) {
        //Salvando Usuário e a role dele
        saveUsuarioAndUsuariosRoles(funcionario);

        if (funcionario.getEndereco() != null) {
            EnderecoFuncionario enderecoFuncionario = funcionario.getEndereco();
            enderecoFuncionarioRepository.save(enderecoFuncionario);
        }

        //Criptografando a senha antes de salvar funcionário
        funcionario.getUsuario().setPassword(passwordEncoder.encode(funcionario.getUsuario().getPassword()));
        funcionario.getUsuario().setActive(true);
        Funcionario funcionarioRetornado =  funcionarioRepository.save(funcionario);
        funcionarioRetornado.getUsuario().setIdClienteFuncionario(funcionarioRetornado.getId());
        usuarioService.saveUsuario(funcionarioRetornado.getUsuario());
        return funcionarioRetornado;
    }

    private void saveUsuarioAndUsuariosRoles(Funcionario funcionario) {
        Set<Role> setRole = new HashSet<>();
        if (funcionario.getCargo().equals("Estoquista")) {
            Role role = roleRepository.findById(3).orElseThrow(() -> new NumberFormatException());
            setRole.add(role);
        } else if (funcionario.getCargo().equals("Administrador")) {
            Role role = roleRepository.findById(1).orElseThrow(() -> new NumberFormatException());
            setRole.add(role);
        }
        if(funcionario.getUsuario().getId() != null) {
            Usuario usuario = usuarioService.findById(funcionario.getUsuario().getId());
            if (!funcionario.getUsuario().getPassword().equals(usuario.getPassword())) {
                funcionario.getUsuario().setPassword(passwordEncoder.encode(funcionario.getUsuario().getPassword()));
            }
        }
        funcionario.getUsuario().setRoles(setRole);
        funcionario.getUsuario().setNome(funcionario.getNome());
        usuarioService.saveUsuario(funcionario.getUsuario());
    }

    public Funcionario saveUpdateFuncionario(Funcionario funcionario) throws NotFoundException {

        if (funcionarioRepository.existsById(funcionario.getId())) {
            if (enderecoFuncionarioRepository.existsById(funcionario.getEndereco().getId())) {
                enderecoFuncionarioRepository.save(funcionario.getEndereco());
            }
            //alterando dados de usuário e roles
            saveUsuarioAndUsuariosRoles(funcionario);
            return funcionarioRepository.save(funcionario);
        }
        throw new NotFoundException("Funcioanrio não cadastrado");
    }


    public void deleteById(long id) {
        Funcionario funcionario = funcionarioRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Não existe funcionário cadastrado"));
        if (funcionario != null) {
            Usuario usuario = usuarioService.verificarEmailExists(funcionario.getUsuario().getUsername());
            if(usuario != null) {
                usuarioService.deleteById(usuario.getId());
            }
            try {
                enderecoFuncionarioRepository.deleteById(funcionario.getEndereco().getId());

            }catch (Exception e){
                System.out.println("Falha ao deletar endereço de funcionário");
            }
            funcionarioRepository.deleteById(id);
        }
    }

    public Funcionario findById(long id) {
        return funcionarioRepository.findById(id)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Funcionário com id " + id + "não existe"));
    }

    public Funcionario verificarCpfJaCadastrado(String cpf) {
       return funcionarioRepository.findByCpf(cpf).orElse(null);
    }
}
