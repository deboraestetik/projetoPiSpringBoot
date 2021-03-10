package com.projetopi.tlgne.controllers;

import com.projetopi.tlgne.entities.Produto;
import com.projetopi.tlgne.services.ImagemService;
import com.projetopi.tlgne.services.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import javassist.NotFoundException;
//@CrossOrigin(origins = "http://localhost:4200")

@RestController
@RequestMapping(value = "/produtos")
public class ProdutoController {


    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private ImagemService imagemService;

    @GetMapping("/produtos")
    public List<Produto> listaProduto() {
        return produtoService.findAll();
    }

    @GetMapping("/{id}")
    public Produto produtoPorId(@PathVariable(value = "id") long id) {
        return produtoService.findById(id);
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

    @PutMapping("/{status}")
    public Produto updateProdutoStatus(@RequestBody Produto produto, @PathVariable(value = "status") long status) throws NotFoundException {
        return produtoService.saveUpdateProdutoStatus(produto , status);
    }

}
