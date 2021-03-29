package com.projetopi.tlgne.controllers;

import com.projetopi.tlgne.entities.Produto;
import com.projetopi.tlgne.services.ImagemService;
import com.projetopi.tlgne.services.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import javassist.NotFoundException;

@RestController
@RequestMapping(value = "/produtos")
public class ProdutoController {


    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private ImagemService imagemService;

    @GetMapping("/login")
    public String entrar() {
        return "login";
    }

    @GetMapping("")
    public List<Produto> listaProduto(@RequestHeader("habilitado") String habilitado) {
        return produtoService.findAll(habilitado);
    }

    @GetMapping("/categoria")
    public List<Produto> listaCategoria(@RequestHeader("categoria")String categoria,
                                        @RequestHeader("habilitado") String habilitado){
        return produtoService.findCategoria(categoria,habilitado);
    }
    
    @GetMapping("/find/{id}")
    public Produto produtoPorId(@PathVariable(value = "id") long id) {
        return produtoService.findById(id);
    }

    @GetMapping("/{semelhanca}")
    public List<Produto> listaProdutoSemelhanca(@PathVariable(value = "semelhanca") String semelhanca,
                                                @RequestHeader("habilitado") String habilitado) {
        return produtoService.findAllSemelanca(semelhanca, habilitado);
    }

    @PostMapping(value = "")
    public Produto saveProduto(@RequestBody Produto produto) {
        return produtoService.saveProduto(produto);
    }

    @DeleteMapping("/{id}")
    public void deleteProduto(@PathVariable(value = "id") long id) {
       produtoService.deleteById(id);
    }

    @PutMapping("")
    public Produto updateProduto(@RequestBody Produto produto) throws NotFoundException {
        return produtoService.saveUpdateProduto(produto);
    }

}
