package com.projetopi.tlgne.controllers;

import com.projetopi.tlgne.entities.Produto;
import com.projetopi.tlgne.services.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import javassist.NotFoundException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping(value = "/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;
    
    @GetMapping("/produtos")
    public List<Produto> listaProduto() {
        return produtoService.findAll();
    }

    @GetMapping("")
    public Produto produtoPorId(@PathVariable(value="id")long id) {
        return produtoService.findById(id);
    }
    
    @PostMapping("")
    public Produto saveProduto(@RequestBody Produto produto){
        return produtoService.saveProduto(produto);
    }

    @DeleteMapping("/{id}")
    public void deleteProduto(@PathVariable(value="id")long id){
        produtoService.deleteById(id);
    }

    @PutMapping("")
    public Produto updateProduto(@RequestBody Produto produto) throws NotFoundException {
        return produtoService.saveUpdateProduto(produto);
    }

}
