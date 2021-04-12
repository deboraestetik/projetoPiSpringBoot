package com.projetopi.tlgne.controllers;


import com.projetopi.tlgne.entities.Venda;
import com.projetopi.tlgne.services.VendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vendas")
public class VendaController {


    @Autowired
    private VendaService vendaService;


    @PostMapping("")
    public Venda saveVenda(@RequestBody Venda venda){
        return vendaService.saveVenda(venda);
    }

    @GetMapping("")
    public List<Venda> findAll(){
        return vendaService.findAll();
    }
}