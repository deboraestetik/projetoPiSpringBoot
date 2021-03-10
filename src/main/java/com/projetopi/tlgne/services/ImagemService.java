package com.projetopi.tlgne.services;

import com.projetopi.tlgne.entities.Imagem;
import com.projetopi.tlgne.entities.Produto;
import com.projetopi.tlgne.repositories.ImagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class ImagemService {

    @Autowired
    private ImagemRepository imagemRepository;

    public ImagemService(ImagemRepository imagemRepository) {
        this.imagemRepository = imagemRepository;
    }

    public ImagemService() {
    }

    public Imagem findById(long id) {
        return imagemRepository.findById(id);
    }

    public List<Imagem> findAll(long id) {
        return imagemRepository.findAllProduto(id);
    }

    public List<byte[]> findAllProdutoImagens(long id) throws IOException {
        return imagemRepository.findAllImagensProduto(id);
    }
}
