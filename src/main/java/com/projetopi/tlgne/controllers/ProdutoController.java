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
@CrossOrigin(origins = "http://localhost:4200")

@RestController
@RequestMapping(value = "/produtos")
public class ProdutoController {


    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private ImagemService imagemService;

    @CrossOrigin
    @GetMapping("/login")
    public String entrar() {
        return "login";
    }

    @CrossOrigin
    @GetMapping("")
    public List<Produto> listaProduto(@RequestHeader("habilitado") String habilitado) {
        return produtoService.findAll(habilitado);
    }

    @CrossOrigin
    @GetMapping("/categoria")
    public List<Produto> listaCategoria(@RequestHeader("categoria")String categoria,
                                        @RequestHeader("habilitado") String habilitado){
        return produtoService.findCategoria(categoria,habilitado);
    }
    
    @CrossOrigin
    @GetMapping("/find/{id}")
    public Produto produtoPorId(@PathVariable(value = "id") long id) {
        return produtoService.findById(id);
    }

    @CrossOrigin
    @GetMapping("/{semelhanca}")
    public List<Produto> listaProdutoSemelhanca(@PathVariable(value = "semelhanca") String semelhanca,
                                                @RequestHeader("habilitado") String habilitado) {
        return produtoService.findAllSemelanca(semelhanca, habilitado);
    }

    @CrossOrigin
    @PostMapping(value = "")
    public Produto saveProduto(@RequestBody Produto produto) {
        return produtoService.saveProduto(produto);
    }

    @CrossOrigin
    @DeleteMapping("/{id}")
    public void deleteProduto(@PathVariable(value = "id") long id) {
       produtoService.deleteById(id);
    }

    @CrossOrigin
    @PutMapping("")
    public Produto updateProduto(@RequestBody Produto produto) throws NotFoundException {
        return produtoService.saveUpdateProduto(produto);
    }

}
