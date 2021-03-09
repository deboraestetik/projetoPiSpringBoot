package com.projetopi.tlgne.controllers;

import com.projetopi.tlgne.entities.Produto;
import com.projetopi.tlgne.services.ProdutoService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
//@CrossOrigin(origins = "http://localhost:4200")

@RestController
@RequestMapping(value = "/produtos")
public class ProdutoController {


    @Autowired
    private ProdutoService produtoService;

    @GetMapping("/produtos")
    public List<Produto> listaProduto() {
        return produtoService.findAll();
    }

    @GetMapping("/{id}")
    public Produto produtoPorId(@PathVariable(value = "id") long id) {
        return produtoService.findById(id);
    }

    @PostMapping(value = "", consumes = {"multipart/form-data"})
    public Produto saveProduto(
            @RequestPart Produto produto, @RequestPart List<MultipartFile> file) throws IOException {
        return produtoService.saveProduto(produto, file);
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
