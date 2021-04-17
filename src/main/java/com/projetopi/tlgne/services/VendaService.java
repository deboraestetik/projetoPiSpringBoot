package com.projetopi.tlgne.services;

import com.projetopi.tlgne.entities.DetalhesVenda;
import com.projetopi.tlgne.entities.Produto;
import com.projetopi.tlgne.entities.Venda;
import com.projetopi.tlgne.repositories.DetalhesVendaRepository;
import com.projetopi.tlgne.repositories.ProdutoRepository;
import com.projetopi.tlgne.repositories.VendaRepository;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VendaService {

    @Autowired
    private VendaRepository vendaRepository;

    @Autowired
    private DetalhesVendaRepository detalhesVendaRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private DetalhesVendaService detalhesVendaService;

    public Venda saveVenda(Venda venda) {

        for (DetalhesVenda detalhesVenda : venda.getDetalhesVenda()) {
            detalhesVenda.setVenda(venda);
            detalhesVendaService.saveDetalhesVenda(detalhesVenda);
        }
        return vendaRepository.save(venda);
    }

    public List<Venda> findAll() {
        return vendaRepository.findAll();
    }

    public Venda findByVenda(Long id) {
        return vendaRepository.findById(id).orElse(null);
    }

    public Venda findVendaClienteById(Long id) {
        return vendaRepository.findVendaClienteById(id);
    }

    public void deleteVendaById(long id) {
        vendaRepository.deleteById(id);
    }

    public List<Produto> findVendasPorCategorias(String dataInicio, String dataFim) {
        List<Produto> listaProdutos = new ArrayList<>();
        List<Venda> listaVendas = vendaRepository.findProdutosIntervaloDatas(dataInicio, dataFim);
        for (Venda venda : listaVendas) {
            List<DetalhesVenda> listaDetalhesVenda = detalhesVendaRepository.findVendaById(venda.getId());
            for (DetalhesVenda detalheVenda : listaDetalhesVenda) {
                long id = detalheVenda.getProduto().getId();
                listaProdutos.add(produtoRepository.findById(id));
            }
        }
        return listaProdutos;
        //return vendaRepository.findProdutosIntervaloDatas(dataInicio, dataFim);
    }

}
