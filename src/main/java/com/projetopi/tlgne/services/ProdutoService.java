package com.projetopi.tlgne.services;

import com.projetopi.tlgne.entities.Produto;
import com.projetopi.tlgne.repositories.ProdutoRepository;
import javassist.NotFoundException;
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

    public Produto saveProduto(Produto produto){
        return produtoRepository.save(produto);
    }

    public void deleteById(long id){
        produtoRepository.deleteById(id);
    }

    public Produto saveUpdateProduto(Produto produto) throws NotFoundException {
        if (produtoRepository.existsById(produto.getId())){
           return produtoRepository.save(produto);
        }
        throw new NotFoundException("Produto não cadastrado");
    }

}