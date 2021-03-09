package com.projetopi.tlgne.controllers;

import com.projetopi.tlgne.entities.Produto;
import com.projetopi.tlgne.services.ProdutoService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
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
    public Produto produtoPorId(@PathVariable(value="id")long id) {
        return produtoService.findById(id);
    }
    
    @PostMapping("")
    public ResponseEntity<?> saveProduto(@RequestParam("arquivos") ArrayList<MultipartFile> arquivos, 
            @RequestBody Produto produto) {
        
        String pasta = "C:\\Users\\Douglas\\Pictures\\imagens\\";
        //@RequestParam("arquivos")
        //List<MultipartFile> arquivos = new ArrayList<>();
        
        if(arquivos != null && !arquivos.isEmpty()) {
            for(MultipartFile arquivo: arquivos) {
                try {
                Path caminhoPasta = Paths.get(pasta);
                Files.createDirectories(caminhoPasta);

                Path caminhoAqruivo = Paths.get(pasta + arquivo.getOriginalFilename());
                produto.setCaminhoImagem(pasta + arquivo.getOriginalFilename());
                Files.write(caminhoAqruivo, arquivo.getBytes());
                } catch(IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        
        produtoService.saveProduto(produto);
        
        return new ResponseEntity(HttpStatus.OK);
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
