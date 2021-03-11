package com.projetopi.tlgne.services;

import com.projetopi.tlgne.entities.Imagem;
import com.projetopi.tlgne.entities.Produto;
import com.projetopi.tlgne.repositories.ImagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
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
        List<String> teste = imagemRepository.findAllImagensProduto(id);
        FileInputStream fis = null;
        List<byte[]> bytes = new ArrayList<>();

        for (String t : teste) {
            File file = new File(t);
            byte[] fileContent = Files.readAllBytes(file.toPath());
            bytes.add(fileContent) ;
      }

        return bytes;
    }
}
