package com.projetopi.tlgne.controllers;

import com.projetopi.tlgne.entities.Produto;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin
@RestController
@RequestMapping("/imagens")
public class ImagemController {
    
    @PostMapping
    public ResponseEntity<?> uploadImagens(@RequestParam("arquivos") ArrayList<MultipartFile> arquivos, 
            @RequestParam("produto") Produto produto) {
        
        if(arquivos.isEmpty()) {
            throw new RuntimeException("O arquivo fornecido não é válido!");
        }
        
        String pasta = "C:\\Users\\Douglas\\Pictures\\imagens\\";
        
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
        
        return new ResponseEntity(HttpStatus.OK);
    }
    
}
