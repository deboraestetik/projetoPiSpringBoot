package com.projetopi.tlgne.services;


import com.projetopi.tlgne.entities.EnderecoFuncionario;
import com.projetopi.tlgne.entities.Funcionario;
import com.projetopi.tlgne.repositories.EnderecoFuncionarioRepository;
import com.projetopi.tlgne.repositories.FuncionarioRepository;
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

    public List<Funcionario> findAll() {
        return funcionarioRepository.findAll();
    }

    public Funcionario findByFuncionario(String funcionario) {
        return funcionarioRepository.findByFuncionario(funcionario);
    }

    public Funcionario saveFuncionario(Funcionario funcionario) {
        EnderecoFuncionario enderecoFuncionario = funcionario.getEndereco();
        enderecoRepository.save(enderecoFuncionario);
        return funcionarioRepository.save(funcionario);
    }

    public Funcionario saveUpdateFuncionario(Funcionario funcionario) throws NotFoundException {

        if (funcionarioRepository.existsById(funcionario.getId())) {
            if(enderecoRepository.existsById(funcionario.getEndereco().getId())){
                enderecoRepository.save(funcionario.getEndereco());
            }
            return funcionarioRepository.save(funcionario);
        }
        throw new NotFoundException("Funcioanrio não cadastrado");
    }


    public void deleteById(long id) {
        Funcionario funcionario = funcionarioRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Não existe funcionário cadastrado"));
        if (funcionario != null){
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
