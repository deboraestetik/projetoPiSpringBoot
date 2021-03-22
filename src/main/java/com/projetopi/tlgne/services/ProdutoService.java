package com.projetopi.tlgne.services;

import com.projetopi.tlgne.entities.Imagem;
import com.projetopi.tlgne.entities.Produto;
import com.projetopi.tlgne.repositories.ImagemRepository;
import com.projetopi.tlgne.repositories.ProdutoRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class ProdutoService {
    
    
 
    

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ImagemService imagemService;


    public ProdutoService() {
    }

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public List<Produto> findAll(String habilitado) {
        if(habilitado.equals("true")){
            return produtoRepository.findAll();

        }
        return null;
    }
    
    public List<Produto> finProdAble() {
        return produtoRepository.findAll();
    }
    
    

    public List<Produto> findAllSemelanca(String semelhanca) {
        return produtoRepository.findAllSemelhanca(semelhanca);
    }

    public Produto findById(long id) {
        return produtoRepository.findById(id);
    }

    public Produto saveProduto(Produto produto) {
        return produtoRepository.save(produto);
    }


    public void deleteById(long id) {

        List<Imagem> imgs = imagemService.findAllProduto(id);//verificando se existe imagens associadas ao produto
        if (imgs.isEmpty()) {
            produtoRepository.deleteById(id);

        } else {
            imagemService.deleteAll(imgs);
            produtoRepository.deleteById(id);
        }
    }



    public Produto saveUpdateProduto(Produto produto) throws NotFoundException {

        if (produtoRepository.existsById(produto.getId())) {
            return produtoRepository.save(produto);
        }
        throw new NotFoundException("Produto não cadastrado");
    }


}
