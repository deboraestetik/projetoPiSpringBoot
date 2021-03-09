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
    public ResponseEntity<?> saveProduto(
            @RequestPart Produto produto, @RequestPart MultipartFile file) throws IOException {

        String pasta = "C:\\Users\\User\\Documents\\projetoPi4\\";
        Produto prod = produto;
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                Path caminhoArquivo = Paths.get(pasta + file.getOriginalFilename());
                Files.write(caminhoArquivo, bytes);

                prod.setCaminhoImagem(pasta + file.getOriginalFilename());


            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }


        produtoService.saveProduto(prod);

        return new

                ResponseEntity(HttpStatus.OK);

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
