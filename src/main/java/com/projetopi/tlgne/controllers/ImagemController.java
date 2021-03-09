package com.projetopi.tlgne.controllers;

import com.projetopi.tlgne.entities.Imagem;
import com.projetopi.tlgne.entities.Produto;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.projetopi.tlgne.services.ImagemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/imagens")
public class ImagemController {

    @Autowired
    private ImagemService imagemService;
//
//    @GetMapping("/{id}")
//    public Imagem imagemPorId(@PathVariable(value = "id") long id) {
//        return imagemService.findById(id);
//    }
//
//
////    @GetMapping("/produto/{id}")
////    public List<Imagem> imagemPorIdProduto(@PathVariable(value = "id") long id) {
////        return imagemService.findByIdProduto(id);
////    }
//}
}