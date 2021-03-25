package com.projetopi.tlgne.controllers;

import com.projetopi.tlgne.entities.Imagem;

import java.io.IOException;
import java.util.List;

import com.projetopi.tlgne.entities.Produto;
import com.projetopi.tlgne.services.ImagemService;
import com.projetopi.tlgne.services.ProdutoService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(origins = "http://localhost:4200")

@RestController
@RequestMapping("/imagens")
public class ImagemController {

    @Autowired
    private ImagemService imagemService;

    @Autowired
    private ProdutoService produtoService;

    @CrossOrigin
    @GetMapping("/{id}")
    public Imagem imagemPorId(@PathVariable(value = "id") long id) {
        return imagemService.findById(id);
    }

    @CrossOrigin
    @PostMapping(value ="/produto/{id}" , consumes = {"multipart/form-data"})
    public Produto saveProdutoComImagem (@RequestPart List<MultipartFile> file,@PathVariable(value = "id") long id,@RequestHeader("favorita") long favoritaPosicao) throws IOException, NotFoundException {
       Produto produtoRecuperado = produtoService.findById(id);
        return imagemService.saveImagemdeProduto(produtoRecuperado, file, favoritaPosicao);
    }

    @CrossOrigin
    @GetMapping("/produto/{id}")
    public List<Imagem> imagemPorIdProduto(@PathVariable (value = "id") long id) throws IOException {
        return imagemService.findAllImagensProduto(id);
    }

    @CrossOrigin
    @DeleteMapping("/{id}")
    public void deleteImagem(@PathVariable(value = "id") long id) {
      imagemService.delete(id);
    }

}
