package com.projetopi.tlgne.services;

import com.projetopi.tlgne.entities.Imagem;
import com.projetopi.tlgne.entities.Produto;
import com.projetopi.tlgne.repositories.ImagemRepository;
import com.projetopi.tlgne.repositories.ProdutoRepository;
import javassist.NotFoundException;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.apache.tomcat.util.http.fileupload.impl.SizeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    private ImagemRepository imagemRepository;

    public ProdutoService() {
    }

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public List<Produto> findAll() {
        return produtoRepository.findAll();
    }

    public Produto findById(long id) {
        return produtoRepository.findById(id);
    }

    public Produto saveProduto(Produto produto){
        return produtoRepository.save(produto);
    }

    public Produto saveProdutoComImagem(Produto produtoSalvo, List<MultipartFile> file) throws IOException {

        if (!file.isEmpty()) {
            saveCaminhoImagem(file, produtoSalvo);
        }

        if (!produtoSalvo.getCaminhoImagem().isEmpty()) {
            saveImagemdb(produtoSalvo);

        }
        return produtoSalvo;
    }


    private void saveImagemdb(Produto produtoSalvo) {

        for (String caminho : produtoSalvo.getCaminhoImagem()) {
            Imagem imagem = new Imagem();
            imagem.setProduto(produtoSalvo);
            imagem.setCaminho(caminho);
            imagemRepository.save(imagem);
        }

    }

    private void saveCaminhoImagem(List<MultipartFile> file, Produto produtoSalvo) {
        try {
            for(MultipartFile f : file) {
                byte[] bytes = f.getBytes();
                Path caminhoArquivo = Paths.get(pasta + f.getOriginalFilename());
                Files.write(caminhoArquivo, bytes);
                produtoSalvo.setCaminhoImagem(pasta + f.getOriginalFilename());
            }
        } catch (IOException e) {
            System.out.println("Error ao salvar caminho imagem");
        }

    }

    public void deleteById(long id) {
        produtoRepository.deleteById(id);
    }

    public Produto saveUpdateProduto(Produto produto) throws NotFoundException {
        if (produtoRepository.existsById(produto.getId())) {
            return produtoRepository.save(produto);
        }
        throw new NotFoundException("Produto não cadastrado");
    }

}