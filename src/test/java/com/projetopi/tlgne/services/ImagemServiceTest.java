package com.projetopi.tlgne.services;


import com.projetopi.tlgne.entities.Imagem;
import com.projetopi.tlgne.entities.Produto;
import com.projetopi.tlgne.repositories.ImagemRepository;
import javassist.NotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@RunWith(SpringJUnit4ClassRunner.class)
public class ImagemServiceTest {
    private ImagemService imagemService;

    private String pasta = "C:\\Users\\User\\Documents\\projetoPi4FrontEnd\\";

    @Mock
    private ImagemRepository imagemRepository;

    @Before
    public void setUp() throws Exception {
        imagemService = new ImagemService(imagemRepository);
    }

    @Test
    public void deveBuscarimagensPorId() {
        Imagem imagem = Mockito.mock(Imagem.class);
        when(imagemRepository.findById(any())).thenReturn(Optional.of(imagem));
        Imagem imagemRetornada = imagemService.findById(anyLong());
        assertEquals(imagem, imagemRetornada);
    }

    @Test
    public void deveSalvarImagem() {
        Imagem imagem = Mockito.mock(Imagem.class);
        imagemService.save(imagem);
        verify(imagemRepository, times(1)).save(imagem);
    }

    @Test
    public void deveBuscarListaDeImagensPeloIdProduto() {
        List<Imagem> imagemList = Mockito.mock(ArrayList.class);
        when(imagemRepository.findAllImagensByProdutoId(anyLong())).thenReturn(imagemList);
        List<Imagem> imagemListRetornado = imagemService.findAllProduto(anyLong());
        assertEquals(imagemList, imagemListRetornado);

    }

//    @Test
//    public void deveBuscarImagensDoProduto() throws IOException {
//        List<Imagem> imagemList = new ArrayList<>();
//        Imagem imagem = Mockito.mock(Imagem.class);
//        imagemList.add(imagem);
//        when(imagemRepository.findAllImagensByProdutoId(anyLong())).thenReturn(imagemList);
//        File file = Mockito.mock(File.class);
//        Path path = Mockito.mock(Path.class);
//
//        byte[] fileContent = new byte[1];
//        when(imagem.getCaminho()).thenReturn(pasta);
//        when(file.toPath()).thenReturn(path);
//        when(Files.readAllBytes(path)).thenReturn(fileContent);
//        List<Imagem> imagemListRetornado = imagemService.findAllImagensProduto(anyLong());
//        assertEquals(imagemList,imagemListRetornado);
//
//    }

    @Test
    public void deveSalvarImagemDoProduto() throws NotFoundException, IOException {
        List<MultipartFile> file = new ArrayList<>();
        MultipartFile multipartFile = Mockito.mock(MultipartFile.class);
        file.add(multipartFile);
        byte[] bytes = new byte[1];
        List<Imagem> imagemList = Mockito.mock(ArrayList.class);
        List<String> stringList = Mockito.mock(ArrayList.class);
        Produto produto = Mockito.mock(Produto.class);
        Long l = Long.valueOf("1").longValue();
        when(multipartFile.getBytes()).thenReturn(bytes);
        when(imagemRepository.findAllImagensByProdutoId(anyLong())).thenReturn(imagemList);
        when(imagemList.isEmpty()).thenReturn(false);
        when(produto.getId()).thenReturn(l);
        when(multipartFile.getOriginalFilename()).thenReturn("Filename");
        when(produto.getCaminhoImagem()).thenReturn(stringList);
        when(stringList.isEmpty()).thenReturn(true);
        Produto produtoRetornado = imagemService.saveImagemdeProduto(produto,file,1);
        assertNotNull(produtoRetornado);

    }

    @Test
    public void deveSalvarImagemDoProdutos() throws NotFoundException, IOException {
        List<MultipartFile> file = new ArrayList<>();
        MultipartFile multipartFile = Mockito.mock(MultipartFile.class);
        file.add(multipartFile);
        byte[] bytes = new byte[1];
        List<Imagem> imagemList = Mockito.mock(ArrayList.class);
        List<String> stringList = Mockito.mock(ArrayList.class);
        Produto produto = Mockito.mock(Produto.class);
        Long l = Long.valueOf("1").longValue();
        when(multipartFile.getBytes()).thenReturn(bytes);
        when(produto.getCaminhoImagem()).thenReturn(stringList);
        when(stringList.isEmpty()).thenReturn(false);
        when(imagemRepository.findAllImagensByProdutoId(anyLong())).thenReturn(imagemList);
        when(imagemList.isEmpty()).thenReturn(false);
        when(produto.getId()).thenReturn(l);
        when(multipartFile.getOriginalFilename()).thenReturn("Filename");
        Produto produtoRetornado = imagemService.saveImagemdeProduto(produto,file,1);
        assertNotNull(produtoRetornado);

    }
    @Test
    public void deveSalvarImagemDoProdutosNãoVazio() throws NotFoundException, IOException {
        List<MultipartFile> file = new ArrayList<>();
        MultipartFile multipartFile = Mockito.mock(MultipartFile.class);
        file.add(multipartFile);
        byte[] bytes = new byte[1];
        List<Imagem> imagemList = Mockito.mock(ArrayList.class);
        List<String> stringList = Mockito.mock(ArrayList.class);
        Produto produto = Mockito.mock(Produto.class);
        Long l = Long.valueOf("1").longValue();
        when(multipartFile.getBytes()).thenReturn(bytes);
        when(produto.getCaminhoImagem()).thenReturn(stringList);
        when(stringList.isEmpty()).thenReturn(false);
        when(imagemRepository.findAllImagensByProdutoId(anyLong())).thenReturn(imagemList);
        when(imagemList.isEmpty()).thenReturn(true);
        when(produto.getId()).thenReturn(l);
        when(multipartFile.getOriginalFilename()).thenReturn("Filename");
        Produto produtoRetornado = imagemService.saveImagemdeProduto(produto,file,1);
        assertNotNull(produtoRetornado);

    }
    @Test
    public void deveSalvarImagemDoProdutosNãoVazioContIgual() throws NotFoundException, IOException {
        List<MultipartFile> file = new ArrayList<>();
        MultipartFile multipartFile = Mockito.mock(MultipartFile.class);
        file.add(multipartFile);
        byte[] bytes = new byte[1];
        List<Imagem> imagemList = Mockito.mock(ArrayList.class);
        List<String> stringList = Mockito.mock(ArrayList.class);
        Produto produto = Mockito.mock(Produto.class);
        Long l = Long.valueOf("1").longValue();
        when(multipartFile.getBytes()).thenReturn(bytes);
        when(produto.getCaminhoImagem()).thenReturn(stringList);
        when(stringList.isEmpty()).thenReturn(false);
        when(imagemRepository.findAllImagensByProdutoId(anyLong())).thenReturn(imagemList);
        when(imagemList.isEmpty()).thenReturn(true);
        when(produto.getId()).thenReturn(l);
        when(multipartFile.getOriginalFilename()).thenReturn("Filename");
        Produto produtoRetornado = imagemService.saveImagemdeProduto(produto,file,0);
        assertNotNull(produtoRetornado);

    }
    @Test
    public void deveEditarImagemfavorita(){
        List<Imagem> imagemList = new ArrayList<>();
        Imagem imagem = Mockito.mock(Imagem.class);
        imagemList.add(imagem);
        Long l = Long.valueOf("1").longValue();
        when(imagemRepository.findAllImagensByProdutoId(anyLong())).thenReturn(imagemList);
        when(imagem.getId()).thenReturn(l);
        imagemService.editarFavorita(l,anyLong());
        verify(imagemRepository,times(1)).save(imagem);

    }

    @Test
    public void deveEditarImagemfavoritaIdDiferente(){
        List<Imagem> imagemList = new ArrayList<>();
        Imagem imagem = Mockito.mock(Imagem.class);
        imagemList.add(imagem);
        Long l = Long.valueOf("1").longValue();
        Long l2 = Long.valueOf("2").longValue();
        when(imagemRepository.findAllImagensByProdutoId(anyLong())).thenReturn(imagemList);
        when(imagem.getId()).thenReturn(l);
        imagemService.editarFavorita(l2,anyLong());
        verify(imagemRepository,times(1)).save(imagem);

    }

    @Test
    public void deveDeletarTodasAsimagens() {
        List<Imagem> imagemList = new ArrayList<>();
        Imagem imagem = Mockito.mock(Imagem.class);
        imagemList.add(imagem);
        File file = Mockito.mock(File.class);
        when(imagem.getCaminho()).thenReturn(pasta);
        when(file.delete()).thenReturn(true);
        imagemService.deleteAll(imagemList);
        verify(imagemRepository, times(1)).deleteAll(imagemList);
    }

    @Test
    public void deveDeletarTodasAsimagensFalse() {
        List<Imagem> imagemList = new ArrayList<>();
        Imagem imagem = Mockito.mock(Imagem.class);
        imagemList.add(imagem);
        File file = Mockito.mock(File.class);
        when(imagem.getCaminho()).thenReturn(pasta);
        when(file.delete()).thenReturn(false);
        imagemService.deleteAll(imagemList);
        verify(imagemRepository, times(1)).deleteAll(imagemList);
    }


    @Test
    public void deveDeletarImagensDosProdutos() {
        Imagem imagem = Mockito.mock(Imagem.class);
        Imagem imagem2 = Mockito.mock(Imagem.class);
        Produto produto = Mockito.mock(Produto.class);
        File file = Mockito.mock(File.class);
        Long l = Long.valueOf("1").longValue();
        List<Imagem> allImagensByProdutoId = Mockito.mock(ArrayList.class);
        allImagensByProdutoId.add(imagem);
        allImagensByProdutoId.add(imagem2);
        when(imagemRepository.findById(anyLong())).thenReturn(Optional.of(imagem));
        when(imagem.getProduto()).thenReturn(produto);
        when(produto.getImagens()).thenReturn(allImagensByProdutoId);
        when(allImagensByProdutoId.size()).thenReturn(2);
        when(imagem.getCaminho()).thenReturn(pasta);
        when(file.delete()).thenReturn(true);
        when(imagemRepository.findAllImagensByProdutoId(l)).thenReturn(allImagensByProdutoId);
        imagemService.delete(l);
        verify(imagemRepository, times(1)).deleteById(l);


    }


}
