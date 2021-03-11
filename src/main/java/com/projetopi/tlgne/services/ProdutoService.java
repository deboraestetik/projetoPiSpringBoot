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

    private String pasta = "C:\\Users\\User\\Documents\\projetoPi4\\";

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ImagemService imagemService;


    public ProdutoService() {
    }

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public List<Produto> findAll() {
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

    public Produto saveProdutoComImagem(Produto produtoSalvo, List<MultipartFile> file) throws NotFoundException {

        if (!file.isEmpty()) {
            saveCaminhoImagem(file, produtoSalvo);
        }

        if (!produtoSalvo.getCaminhoImagem().isEmpty()) {
            saveImagemdb(file,produtoSalvo);

        }
        return produtoSalvo;
    }


    private void saveImagemdb(List<MultipartFile> file, Produto produtoSalvo) {

        for (MultipartFile f : file) {
            Imagem imagem = new Imagem();
            imagem.setProduto(produtoSalvo);
            imagem.setCaminho(pasta + (produtoSalvo.getId().toString()) + f.getOriginalFilename());
            imagemService.save(imagem);
        }

    }

    private void saveCaminhoImagem(List<MultipartFile> file, Produto produtoSalvo) {
        try {
            for (MultipartFile f : file) {
                byte[] bytes = f.getBytes();
                Path caminhoArquivo = Paths.get(pasta + (produtoSalvo.getId().toString()) + f.getOriginalFilename());
                Files.write(caminhoArquivo, bytes);
                produtoSalvo.setCaminhoImagem(pasta + (produtoSalvo.getId().toString()) + f.getOriginalFilename());
            }
        } catch (IOException e) {
            System.out.println("Error ao salvar caminho imagem");
        }

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
            return produtoRepository.saveAndFlush(produto);
        }
        throw new NotFoundException("Produto não cadastrado");
    }


}
