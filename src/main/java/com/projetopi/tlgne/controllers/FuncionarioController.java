package com.projetopi.tlgne.controllers;


import com.projetopi.tlgne.entities.Funcionario;
import com.projetopi.tlgne.services.FuncionarioService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/funcionarios")
public class FuncionarioController {

    @Autowired
    private FuncionarioService funcionarioService;

    @GetMapping("")
    public List<Funcionario> findAll(){
        return funcionarioService.findAll();
    }

    @GetMapping("/{usuario}")
    public Funcionario findBy(@PathVariable(value = "funcionario") String funcionario){
        return funcionarioService.findByFuncionario(funcionario);
    }

    @GetMapping("/find/{id}")
    public Funcionario findById(@PathVariable(value = "id") long id){
        return funcionarioService.findById(id);
    }

    @CrossOrigin
    @PostMapping(value = "")
    public Funcionario saveFuncionario(@RequestBody Funcionario funcionario) {
        return funcionarioService.saveFuncionario(funcionario);
    }

    @CrossOrigin
    @PutMapping("")
    public Funcionario updateFuncionario(@RequestBody Funcionario funcionario) throws NotFoundException {
        return funcionarioService.saveUpdateFuncionario(funcionario);
    }

    @CrossOrigin
    @DeleteMapping("/{id}")
    public void deleteFuncionario(@PathVariable(value = "id") long id) {
        funcionarioService.deleteById(id);
    }
}
