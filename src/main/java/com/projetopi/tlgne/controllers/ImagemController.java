package com.projetopi.tlgne.controllers;


import com.projetopi.tlgne.services.ImagemService;
import com.projetopi.tlgne.services.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "")
public class ImagemController {

    @Autowired
    private ImagemService imagemService;

   @Autowired
   private ProdutoService produtoService;


}
