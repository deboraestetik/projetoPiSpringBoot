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
        Usuario usuario = new Usuario();
        //Salvando Usuário e a role dele
        saveUsuarioAndUsuariosRoles(funcionario, usuario);

        if (funcionario.getEndereco() != null) {
            EnderecoFuncionario enderecoFuncionario = funcionario.getEndereco();
            enderecoFuncionarioRepository.save(enderecoFuncionario);
        }

        //Criptografando a senha antes de salvar funcionário
        funcionario.setSenha(passwordEncoder.encode(funcionario.getSenha()));
        return funcionarioRepository.save(funcionario);
    }

    private void saveUsuarioAndUsuariosRoles(Funcionario funcionario, Usuario usuario) {
        Set<Role> setRole = new HashSet<>();
        if (funcionario.getCargo().equals("Estoquista")) {
            Role role = roleRepository.findById(3).orElseThrow(() -> new NumberFormatException());
            setRole.add(role);
        } else if (funcionario.getCargo().equals("Administrador")) {
            Role role = roleRepository.findById(1).orElseThrow(() -> new NumberFormatException());
            setRole.add(role);
        }
        usuario.setUsername(funcionario.getEmail());
        if(!passwordEncoder.matches(funcionario.getSenha(),usuario.getPassword())){
            usuario.setPassword(passwordEncoder.encode(funcionario.getSenha()));
        }
        usuario.setNome(funcionario.getNome());
        usuario.setActive(funcionario.isStatus());
        usuario.setRoles(setRole);
        usuarioService.saveUsuario(usuario);
    }

    public Funcionario saveUpdateFuncionario(Funcionario funcionario) throws NotFoundException {

        if (funcionarioRepository.existsById(funcionario.getId())) {
            if (enderecoFuncionarioRepository.existsById(funcionario.getEndereco().getId())) {
                enderecoFuncionarioRepository.save(funcionario.getEndereco());
            }
            Usuario usuario = usuarioService.verificarEmailExists(funcionario.getEmail());
            //alterando dados de usuário e roles
            saveUsuarioAndUsuariosRoles(funcionario, usuario);
            return funcionarioRepository.save(funcionario);
        }
        throw new NotFoundException("Funcioanrio não cadastrado");
    }


    public void deleteById(long id) {
        Funcionario funcionario = funcionarioRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Não existe funcionário cadastrado"));
        if (funcionario != null) {
            Usuario usuario = usuarioService.verificarEmailExists(funcionario.getEmail());
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
