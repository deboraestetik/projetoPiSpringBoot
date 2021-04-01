package com.projetopi.tlgne.services;


import com.projetopi.tlgne.entities.EnderecoFuncionario;
import com.projetopi.tlgne.entities.Funcionario;
import com.projetopi.tlgne.entities.Usuario;
import com.projetopi.tlgne.repositories.EnderecoFuncionarioRepository;
import com.projetopi.tlgne.repositories.FuncionarioRepository;
import com.projetopi.tlgne.repositories.MyRepository;
import com.projetopi.tlgne.repositories.UsuarioRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FuncionarioService {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private EnderecoFuncionarioRepository enderecoRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private MyRepository repository;

    public List<Funcionario> findAll() {
        return funcionarioRepository.findAll();
    }

    public Funcionario findByFuncionario(String funcionario) {
        return funcionarioRepository.findByFuncionario(funcionario);
    }

    public Funcionario saveFuncionario(Funcionario funcionario) {
        if(funcionario.getEndereco() != null) {
            EnderecoFuncionario enderecoFuncionario = funcionario.getEndereco();
            enderecoRepository.save(enderecoFuncionario);
        }
            Funcionario funcionarioRetornado = funcionarioRepository.save(funcionario);
            if(funcionarioRetornado.getCargo().equals("Estoquista")){
                repository.isertRoles(funcionarioRetornado.getId(),3);
            }else if(funcionarioRetornado.getCargo().equals("Cliente")){
                repository.isertRoles(funcionarioRetornado.getId(),2);
            }else if(funcionarioRetornado.getCargo().equals("Admin")) {
                repository.isertRoles(funcionarioRetornado.getId(), 1);
            }
            return funcionarioRetornado;
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
