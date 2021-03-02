package com.projetopi.tlgne.services;


import com.projetopi.tlgne.entities.Produto;
//import com.projetopi.tlgne.repositories.ProdutoRepository;
import com.projetopi.tlgne.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public ProdutoService() {
    }

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public List<Produto> findAll(){
        return produtoRepository.findAll();
    }

    public Produto findById(long id){
        return produtoRepository.findById(id);
    }
}
