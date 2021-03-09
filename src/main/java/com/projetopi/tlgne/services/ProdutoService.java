package com.projetopi.tlgne.services;

import com.projetopi.tlgne.entities.Imagem;
import com.projetopi.tlgne.entities.Produto;
import com.projetopi.tlgne.repositories.ImagemRepository;
import com.projetopi.tlgne.repositories.ProdutoRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ImagemRepository imagemRepository;

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
        Produto produtoSalvo = produtoRepository.save(produto);
        
        if(produto.getCaminhoImagem() != null) {
//            for(String caminho: produto.getCaminhoImagem()){
                Imagem imagem = new Imagem();
                imagem.setProduto(produtoSalvo);
                imagem.setCaminho(produto.getCaminhoImagem());
                imagemRepository.save(imagem);
            }
            

        return produtoSalvo;
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