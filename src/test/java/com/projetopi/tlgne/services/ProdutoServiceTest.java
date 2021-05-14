package com.projetopi.tlgne.services;


import com.projetopi.tlgne.entities.Imagem;
import com.projetopi.tlgne.entities.Produto;
import com.projetopi.tlgne.repositories.ProdutoRepository;
import javassist.NotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class ProdutoServiceTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private ImagemService imagemService;

    private ProdutoService produtoService;

    @Before
    public void setUp() throws Exception {
        produtoService = new ProdutoService(produtoRepository,imagemService);
    }

    @Test
    public void deveBuscarProdutosPorCategoriaHabilitados(){
        List<Produto> produtoList = Mockito.mock(ArrayList.class);
        when(produtoRepository.findAllCategoria(anyString(),any())).thenReturn(produtoList);
        List<Produto> produtoListRetornado = produtoService.findCategoria("cama", "true");
        assertEquals(produtoList,produtoListRetornado);
    }

    @Test
    public void deveBuscarProdutosPorCategoria(){
        List<Produto> produtoList = Mockito.mock(ArrayList.class);
        when(produtoRepository.findAllCategoria(any())).thenReturn(produtoList);
        List<Produto> produtoListRetornado = produtoService.findCategoria("cama", "false");
        assertEquals(produtoList,produtoListRetornado);
    }


    @Test
    public void deveBuscarProdutosHabilitados(){
        List<Produto> produtoList = Mockito.mock(ArrayList.class);
        when(produtoRepository.findAllProdutoHabilitado(anyString())).thenReturn(produtoList);
        List<Produto> produtoListRetornado = produtoService.findAll("true");
        assertEquals(produtoList,produtoListRetornado);
    }


    @Test
    public void deveBuscarProdutosNaoHabilitados(){
        List<Produto> produtoList = Mockito.mock(ArrayList.class);
        when(produtoRepository.findAll()).thenReturn(produtoList);
        List<Produto> produtoListRetornado = produtoService.findAll("false");
        assertEquals(produtoList,produtoListRetornado);
    }

    @Test
    public void deveBuscarProdutosHabilitadosNull(){
        List<Produto> produtoList = Mockito.mock(ArrayList.class);
        when(produtoRepository.findAllProdutoHabilitado(anyString())).thenReturn(produtoList);
        List<Produto> produtoListRetornado = produtoService.findAll("null");
        assertNull(produtoListRetornado);
    }

    @Test
    public void deveBuscarProdutosPorSemelhancaEHabilitados(){
        List<Produto> produtoList = Mockito.mock(ArrayList.class);
        when(produtoRepository.findAllSemelhanca(anyString(),any())).thenReturn(produtoList);
        List<Produto> produtoListRetornado = produtoService.findAllSemelanca("Seme","true");
        assertEquals(produtoList,produtoListRetornado);
    }



    @Test
    public void deveBuscarProdutosPorSemelhancaENaoHabilitados(){
        List<Produto> produtoList = Mockito.mock(ArrayList.class);
        when(produtoRepository.findAllSemelhanca(anyString())).thenReturn(produtoList);
        List<Produto> produtoListRetornado = produtoService.findAllSemelanca("seme","false");
        assertEquals(produtoList,produtoListRetornado);
    }



    @Test
    public void deveBuscarProdutoPorId(){
        Produto produto = Mockito.mock(Produto.class);
        when(produtoRepository.findById(anyLong())).thenReturn(produto);
        Produto produtoRetornado = produtoService.findById(anyLong());
        assertEquals(produto,produtoRetornado);

    }

    @Test
    public void deveSalvarProduto(){
        Produto produto = Mockito.mock(Produto.class);
        when(produtoRepository.save(any())).thenReturn(produto);
        Produto produtoRetornado = produtoService.saveProduto(any());
        assertEquals(produto,produtoRetornado);
    }

    @Test
    public void deveDeletarProdutosPorId(){
        List<Imagem> imgs = Mockito.mock(ArrayList.class);
        when(imagemService.findAllProduto(anyLong())).thenReturn(imgs);
        produtoService.deleteById(anyLong());
        verify(produtoRepository, times(1)).deleteById(anyLong());

    }

    @Test
    public void deveDeletarProdutosPorIdComImagens(){
        List<Imagem> imgs = new ArrayList<>();
        when(imagemService.findAllProduto(anyLong())).thenReturn(imgs);
        produtoService.deleteById(anyLong());
        verify(produtoRepository, times(1)).deleteById(anyLong());

    }

    @Test
    public void deveAlterarESalvarProduto() throws NotFoundException {
        Produto produto = Mockito.mock(Produto.class);
        when(produtoRepository.existsById(anyLong())).thenReturn(true);
        when(produtoRepository.save(any())).thenReturn(produto);
        Produto produtoRetornado = produtoService.saveUpdateProduto(produto);
        assertEquals(produto,produtoRetornado);

    }

    @Test(expected = NotFoundException.class)
    public void deveLancarExceptionQuandoAlterarProduto() throws NotFoundException {
        Produto produto = Mockito.mock(Produto.class);
        when(produtoRepository.existsById(anyLong())).thenReturn(false);
        produtoService.saveUpdateProduto(produto);
    }


}
