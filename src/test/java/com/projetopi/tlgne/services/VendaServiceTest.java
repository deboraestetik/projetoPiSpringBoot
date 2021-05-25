package com.projetopi.tlgne.services;


import com.projetopi.tlgne.entities.*;
import com.projetopi.tlgne.repositories.DetalhesVendaRepository;
import com.projetopi.tlgne.repositories.FreteRepository;
import com.projetopi.tlgne.repositories.ProdutoRepository;
import com.projetopi.tlgne.repositories.VendaRepository;
import java.time.Instant;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class VendaServiceTest {

    private VendaService vendaService;

    @Mock
    private VendaRepository vendaRepository;

    @Mock
    private DetalhesVendaRepository detalhesVendaRepository;

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private DetalhesVendaService detalhesVendaService;

    @Mock
    private FreteRepository freteRepository;

    @Before
    public void setUp() throws Exception {
        vendaService = new VendaService(vendaRepository, detalhesVendaRepository, produtoRepository, detalhesVendaService,freteRepository);
    }

    @Test
    public void deveSalvarVenda(){
        Venda venda = Mockito.mock(Venda.class);
        Cliente cliente = Mockito.mock(Cliente.class);
        List<DetalhesVenda> detalhesVendaList = new ArrayList<>();
        DetalhesVenda detalhesVenda = new DetalhesVenda();
        detalhesVendaList.add(detalhesVenda);
        Long l = Long.valueOf("1").longValue();
        Frete frete = Mockito.mock(Frete.class);
        when(venda.getCliente()).thenReturn(cliente);
        when(cliente.getId()).thenReturn(l);
        when(venda.getPagamento()).thenReturn("Boleto");
        when(freteRepository.save(any())).thenReturn(frete);
        when(vendaRepository.save(any())).thenReturn(venda);
        when(venda.getDetalhesVenda()).thenReturn(detalhesVendaList);
        Venda vendaretornada = vendaService.saveVenda(venda);
        assertEquals(venda,vendaretornada);
    }

    @Test
    public void deveBuscarListaDeVendas(){
        List<Venda> vendaList = Mockito.mock(ArrayList.class);
        when(vendaRepository.findAll()).thenReturn(vendaList);
        List<Venda> vendaListRetornado = vendaService.findAll();
        assertEquals(vendaList,vendaListRetornado);
    }

    @Test
    public void debeBuscarTodasAsVendasPorData(){
        List<Venda> vendaList = Mockito.mock(ArrayList.class);
        when(vendaRepository.findVendasPorPeriodo(Date.from(Instant.MIN),Date.from(Instant.MIN))).thenReturn(vendaList);
        when(vendaList.size()).thenReturn(1);
        int vendaListRetornado = vendaService.findAllVenda(Date.from(Instant.MIN),Date.from(Instant.MIN));
        assertEquals(vendaListRetornado,1);
    }
    @Test
    public void deveBuscarTotalDeProdutosVendidos(){
        List<DetalhesVenda> detalhesVendaList = new ArrayList<>();
        Venda venda = new Venda();
        List<Venda> vendaList = new ArrayList<>();
        DetalhesVenda detalhesVenda = Mockito.mock(DetalhesVenda.class);
        Produto produto = Mockito.mock(Produto.class);
        detalhesVendaList.add(detalhesVenda);
        vendaList.add(venda);
        Long l = Long.valueOf("1").longValue();
        when(vendaRepository.findVendasPorPeriodo(Date.from(Instant.MIN),Date.from(Instant.MIN))).thenReturn(vendaList);
        when(detalhesVendaRepository.findVendaById(anyLong())).thenReturn(detalhesVendaList);
        when(detalhesVenda.getProduto()).thenReturn(produto);
        when(produto.getId()).thenReturn(l);
        when(produtoRepository.findById(anyLong())).thenReturn(produto);
        int retorno = vendaService.totalProdutosVendidos(Date.from(Instant.MIN),Date.from(Instant.MIN));
        assertEquals(retorno,1);

    }

    @Test
    public void deveBuscarVendaPorId(){
        Venda venda = Mockito.mock(Venda.class);
        when(vendaRepository.findById(anyLong())).thenReturn(Optional.of(venda));
        Venda vendaRetornado = vendaService.findByVenda(anyLong());
        assertEquals(venda,vendaRetornado);
    }

    @Test
    public void deveBuscarListaDeVendaPorIdCliente(){
        List<Venda> vendaList = Mockito.mock(ArrayList.class);
        when(vendaRepository.findVendaClienteById(anyLong())).thenReturn(vendaList);
        List<Venda> vendaListRetornado = vendaService.findVendaClienteById(anyLong());
        assertEquals(vendaList,vendaListRetornado);
    }

    @Test
    public void deveBuscarVendaPorNumerodePedido(){
        List<Venda> vendaList = Mockito.mock(ArrayList.class);
        when(vendaRepository.findVendaByNumeroPedido(anyLong(),anyString())).thenReturn(vendaList);
        List<Venda> vendaListRetornado = vendaService.findVendaNumeroPedido(anyLong(),anyString());
        assertEquals(vendaList,vendaListRetornado);
    }

    @Test
    public void deveDeletarVendaporId(){
        vendaService.deleteVendaById(anyLong());
        verify(vendaRepository,times(1)).deleteById(anyLong());
    }


}
