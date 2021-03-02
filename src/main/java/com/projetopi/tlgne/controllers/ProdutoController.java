package com.projetopi.tlgne.controllers;


import com.projetopi.tlgne.entities.Produto;
import com.projetopi.tlgne.services.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

//    @GetMapping
//    public ResponseEntity<Produto> findAll() {
//        Produto p = new Produto();
//        return ResponseEntity.ok().body(p);
//
//    }

    @GetMapping("/produtos")
    public List<Produto> listaProduto() {
        return produtoService.findAll();
    }

    @GetMapping("")
    public Produto produtoPorId(@PathVariable(value="id")long id) {
        return produtoService.findById(id);
    }



}

