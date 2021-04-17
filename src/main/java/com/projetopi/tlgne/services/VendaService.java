package com.projetopi.tlgne.services;

import com.projetopi.tlgne.entities.CategoriaPorcentagem;
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

    public List<CategoriaPorcentagem> findVendasPorCategorias(String dataInicio, String dataFim) {
        List<Produto> listaProdutos = new ArrayList<>();
        List<CategoriaPorcentagem> listaCategoriaPorcentagem = new ArrayList<>();
        int totalProdutos = 0, cama = 0, mesa = 0, banho = 0, decoracao = 0;
        List<Venda> listaVendas = vendaRepository.findProdutosIntervaloDatas(dataInicio, dataFim);
        for (Venda venda : listaVendas) {
            List<DetalhesVenda> listaDetalhesVenda = detalhesVendaRepository.findVendaById(venda.getId());
            for (DetalhesVenda detalheVenda : listaDetalhesVenda) {
                long id = detalheVenda.getProduto().getId();
                Produto p = produtoRepository.findById(id);
                if(p.getCategoria().equals("Cama")) {
                    cama++;
                } else if (p.getCategoria().equals("Mesa")) {
                    mesa++;
                } else if (p.getCategoria().equals("Banho")) {
                    banho++;
                } else if (p.getCategoria().equals("Decoração")) {
                    decoracao++;
                }
                listaProdutos.add(p);
            }
        }
        totalProdutos = listaProdutos.size();
        float camaPorcento = (float)cama / (float)totalProdutos * 100;
        float mesaPorcento = (float)mesa / (float)totalProdutos * 100;
        float banhoPorcento = (float)banho / (float)totalProdutos * 100;
        float decoracaoPorcento = (float)decoracao / (float)totalProdutos * 100;
        
        listaCategoriaPorcentagem.add(new CategoriaPorcentagem("Cama", camaPorcento));
        listaCategoriaPorcentagem.add(new CategoriaPorcentagem("Mesa", mesaPorcento));
        listaCategoriaPorcentagem.add(new CategoriaPorcentagem("Banho", banhoPorcento));
        listaCategoriaPorcentagem.add(new CategoriaPorcentagem("Decoração", decoracaoPorcento));
        
        return listaCategoriaPorcentagem;
    }

}
