package com.projetopi.tlgne.services;

import com.projetopi.tlgne.entities.CategoriaPorcentagem;
import com.projetopi.tlgne.entities.DetalhesVenda;
import com.projetopi.tlgne.entities.MesVendas;
import com.projetopi.tlgne.entities.Meses;
import com.projetopi.tlgne.entities.Produto;
import com.projetopi.tlgne.entities.Venda;
import com.projetopi.tlgne.repositories.DetalhesVendaRepository;
import com.projetopi.tlgne.repositories.ProdutoRepository;
import com.projetopi.tlgne.repositories.VendaRepository;
import java.sql.Date;
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

    // here
    public int findAllVenda(String dataInico, String dataFim) {
        List<Venda> listaVendas = vendaRepository.findVendasPorPeriodo(dataInico, dataFim);
        return listaVendas.size();
    }

    //here
    public int totalProdutosVendidos(String dataInicio, String dataFim) {
        List<Produto> listaProdutos = new ArrayList<>();
        int totProd = 0;
        List<Venda> listaVendas = vendaRepository.findVendasPorPeriodo(dataInicio, dataFim);
        for (Venda vendas : listaVendas) {
            List<DetalhesVenda> listaDetalhesVenda = detalhesVendaRepository.findVendaById(vendas.getId());
            for (DetalhesVenda detalheVenda : listaDetalhesVenda) {
                long id = detalheVenda.getProduto().getId();
                Produto p = produtoRepository.findById(id);
                listaProdutos.add(p);

            }

        }

        return listaProdutos.size();
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

    public List<CategoriaPorcentagem> findVendasCategoriasPorcentagem(String dataInicio, String dataFim) {
        List<Produto> listaProdutos = new ArrayList<>();
        List<CategoriaPorcentagem> listaCategoriaPorcentagem = new ArrayList<>();
        int totalProdutos = 0, cama = 0, mesa = 0, banho = 0, decoracao = 0;
        List<Venda> listaVendas = vendaRepository.findVendasPorPeriodo(dataInicio, dataFim);
        if (listaVendas.size() > 0) {
            for (Venda venda : listaVendas) {
                List<DetalhesVenda> listaDetalhesVenda = detalhesVendaRepository.findVendaById(venda.getId());
                for (DetalhesVenda detalheVenda : listaDetalhesVenda) {
                    long id = detalheVenda.getProduto().getId();
                    Produto p = produtoRepository.findById(id);
                    if (p.getCategoria().equals("Cama")) {
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
            float camaPorcento = (float) cama / (float) totalProdutos * 100;
            float mesaPorcento = (float) mesa / (float) totalProdutos * 100;
            float banhoPorcento = (float) banho / (float) totalProdutos * 100;
            float decoracaoPorcento = (float) decoracao / (float) totalProdutos * 100;

            listaCategoriaPorcentagem.add(new CategoriaPorcentagem("Cama", camaPorcento));
            listaCategoriaPorcentagem.add(new CategoriaPorcentagem("Mesa", mesaPorcento));
            listaCategoriaPorcentagem.add(new CategoriaPorcentagem("Banho", banhoPorcento));
            listaCategoriaPorcentagem.add(new CategoriaPorcentagem("Decoração", decoracaoPorcento));
        } else {
            listaCategoriaPorcentagem.add(new CategoriaPorcentagem("Cama", (float)cama));
            listaCategoriaPorcentagem.add(new CategoriaPorcentagem("Mesa", (float)mesa));
            listaCategoriaPorcentagem.add(new CategoriaPorcentagem("Banho", (float)banho));
            listaCategoriaPorcentagem.add(new CategoriaPorcentagem("Decoração", (float)decoracao));
        }

        return listaCategoriaPorcentagem;
    }

    public List<String> findVendasByDia(String dataInicio, String dataFim) {
        List<Venda> vendas = vendaRepository.findVendasPorPeriodo(dataInicio, dataFim);
        List<String> vendaPorDia = new ArrayList<>();
        int qtdVendida = 0;

        if (vendas.size() > 0) {
            String dataAtualStr = vendas.get(0).getDataVenda().toString();
            dataAtualStr = dataAtualStr.substring(0, 10);
            Date dataAtual = Date.valueOf(dataAtualStr);

            for (Venda venda : vendas) {
                String dataVendaStr = venda.getDataVenda().toString();
                dataVendaStr = dataVendaStr.substring(0, 10);
                Date dataVenda = Date.valueOf(dataVendaStr);

                if (dataAtual.equals(dataVenda)) {
                    qtdVendida++;
                } else if (dataAtual.getDay() < dataVenda.getDay()) {
                    vendaPorDia.add(dataAtual.toString() + " " + qtdVendida);
                    dataAtual.setDate(dataAtual.getDate() + 1);
                    qtdVendida = 0;
                }
            }
            vendaPorDia.add(dataAtual.toString() + " " + (qtdVendida + 1));
        } else {
            vendaPorDia.add("Não há vendas nesse período!");
            return vendaPorDia;
        }

        return vendaPorDia;
    }

    public List<MesVendas> findVendasByMes(String dataInicio, String dataFim) {
        List<Venda> vendas = vendaRepository.findVendasPorPeriodo(dataInicio, dataFim);
        List<MesVendas> qtdVendasMes = new ArrayList<>();
        int mesInicio = Date.valueOf(dataInicio).getMonth();
        int mesFim = Date.valueOf(dataFim).getMonth();
        int qtdVendida = 0, mesVenda = 0;

        if (vendas.size() > 0) {
            if (mesInicio == mesFim) {
                for (Venda venda : vendas) {
                    qtdVendida++;
                }
                qtdVendasMes.add(new MesVendas(Meses.values()[mesInicio].getNomeAbreviado(), qtdVendida));
                return qtdVendasMes;
            } else {
                for (Venda venda : vendas) {
                    String dataVendaStr = venda.getDataVenda().toString();
                    dataVendaStr = dataVendaStr.substring(0, 10);
                    Date dataVenda = Date.valueOf(dataVendaStr);
                    mesVenda = dataVenda.getMonth();
                    if (mesInicio < mesVenda && qtdVendida == 0) {
                        while (mesInicio < mesVenda) {
                            qtdVendasMes.add(new MesVendas(Meses.values()[mesInicio].getNomeAbreviado(), qtdVendida));
                            mesInicio++;
                        }
                    }
                    if (mesInicio == mesVenda) {
                        qtdVendida++;
                    }
                }
                if (qtdVendida > 0) {
                    qtdVendasMes.add(new MesVendas(Meses.values()[mesVenda].getNomeAbreviado(), qtdVendida));
                    if (mesVenda < mesFim) {
                        qtdVendida = 0;
                        while (mesVenda < mesFim) {
                            mesVenda++;
                            qtdVendasMes.add(new MesVendas(Meses.values()[mesVenda].getNomeAbreviado(), qtdVendida));
                        }
                    }
                }
            }
        } else {
            while (mesInicio <= mesFim) {
                qtdVendasMes.add(new MesVendas(Meses.values()[mesInicio].getNomeAbreviado(), qtdVendida));
                mesInicio++;
            }
            return qtdVendasMes;
        }
        return qtdVendasMes;
    }

}
