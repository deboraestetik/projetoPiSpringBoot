package com.projetopi.tlgne.services;

import com.projetopi.tlgne.entities.Imagem;
import com.projetopi.tlgne.entities.Produto;
import com.projetopi.tlgne.repositories.ImagemRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class ImagemService {

    private String pasta = "C:\\Users\\User\\Documents\\projetoPi4FrontEnd\\";

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


    public void save(Imagem imagem) {
        imagemRepository.save(imagem);
    }

    public List<Imagem> findAllProduto(long id) {
        return imagemRepository.findAllImagensByProdutoId(id);
    }

    public List<Imagem> findAllImagensProduto(long id) throws IOException {
        List<Imagem> imagens = imagemRepository.findAllImagensByProdutoId(id);
        List<Imagem> imagemList = new ArrayList<>();

        for (Imagem caminho : imagens) {
            File file = new File(caminho.getCaminho());
            byte[] fileContent = Files.readAllBytes(file.toPath());
            Imagem img = caminho;
            img.setImagem(fileContent);
            imagemList.add(img);
        }

        return imagemList;
    }

    public Produto saveImagemdeProduto(Produto produtoSalvo, List<MultipartFile> file, long imagemFavorita) throws NotFoundException {
        if (!file.isEmpty()) {
            saveCaminhoImagem(file, produtoSalvo);
        }

        if (!produtoSalvo.getCaminhoImagem().isEmpty()) {
            saveImagemdb(file, produtoSalvo, imagemFavorita);

        }
        return produtoSalvo;
    }


    private void saveImagemdb(List<MultipartFile> file, Produto produtoSalvo, long imagemFavorita) {
        List<Imagem> imgs = imagemRepository.findAllImagensByProdutoId(produtoSalvo.getId());//verificando se existe imagens associadas ao produto
        //long favorita = Long.parseLong(imagemFavorita);
        int cont = 0;
        if (imgs.isEmpty()) { //Salvar
            for (MultipartFile f : file) {
                Imagem imagem = new Imagem();
                imagem.setProduto(produtoSalvo);
                imagem.setCaminho(pasta + (produtoSalvo.getId().toString()) + f.getOriginalFilename());
                if (cont == imagemFavorita) {
                    imagem.setImagemPrincipal(true);
                } else {
                    imagem.setImagemPrincipal(false);
                }
                imagemRepository.save(imagem);
                cont++;

            }
        } else { // Editar
            editarImagemdb(file, produtoSalvo);
        }
    }

    private void editarImagemdb(List<MultipartFile> file, Produto produtoSalvo) {
        for (MultipartFile f : file) {
            Imagem imagem = new Imagem();
            imagem.setProduto(produtoSalvo);
            imagem.setCaminho(pasta + (produtoSalvo.getId().toString()) + f.getOriginalFilename());
            imagem.setImagemPrincipal(false);
            imagemRepository.save(imagem);
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

    public void editarFavorita(long idImagem, long idProduto) {
        List<Imagem> imgsEditadas = imagemRepository.findAllImagensByProdutoId(idProduto);
        for (Imagem img : imgsEditadas) {
            if (img.getId() == idImagem) {
                img.setImagemPrincipal(true);
            } else {
                img.setImagemPrincipal(false);
            }
            imagemRepository.save(img);
        }
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
        } catch (Exception e) {
            System.out.println("Error ao deletar da pasta");
        }
        imagemRepository.deleteAll(imgs);
    }

    public void delete(long id) {
        Imagem imagem = imagemRepository.findById(id);
        if (imagem.getProduto().getImagens().size() > 1) {
            Produto produto = imagem.getProduto();
            try {
                File file = new File(imagem.getCaminho());
                if (file.delete()) {
                    System.out.println(file.getName() + "Deletado!");
                } else {
                    System.out.println("Delete operation is failed.");
                }
            } catch (Exception e) {
                System.out.println("Error ao deletar da pasta");
            }
            imagemRepository.deleteById(id);
            List<Imagem> allImagensByProdutoId = imagemRepository.findAllImagensByProdutoId(produto.getId());
            if (allImagensByProdutoId.size() == 1) {
                allImagensByProdutoId.get(0).setImagemPrincipal(true);
                imagemRepository.save(allImagensByProdutoId.get(0));
            }
        }
    }
}
