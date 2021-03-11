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

//    public List<Imagem> findAll(long id) {
//        return imagemRepository.findAllProduto(id);
//    }

    public void save(Imagem imagem){
        imagemRepository.save(imagem);
    }

    public List<Imagem> findAllProdutoImagens(long id) throws IOException {
        List<Imagem> imagens = imagemRepository.findAllImagens(id);
        List<Imagem> imagemList = new ArrayList<>();
       // FileInputStream fis = null;
      //  List<byte[]> bytes = new ArrayList<>();

        for (Imagem caminho : imagens) {
            File file = new File(caminho.getCaminho());
            byte[] fileContent = Files.readAllBytes(file.toPath());
            //bytes.add(fileContent);
            Imagem img = caminho;
            img.setImagem(fileContent);
            imagemList.add(img);
      }

        return imagemList;
    }
    public void deleteAll(List<Imagem> imgs) {
            try {
                for (Imagem img : imgs) {

                    File file = new File(img.getCaminho());
                    if (file.delete()) {
                        System.out.println(file.getName() + " is deleted!");
                    } else {
                        System.out.println("Delete operation is failed.");
                    }

                }
            }catch (Exception e){
                System.out.println("Error ao deletar da pasta");
            }
        imagemRepository.deleteAll(imgs);
    }

    public void delete(Imagem imagem){}

    public List<Imagem> findAllProduto(long id) {
        return imagemRepository.findAllImagens(id);
    }
}
