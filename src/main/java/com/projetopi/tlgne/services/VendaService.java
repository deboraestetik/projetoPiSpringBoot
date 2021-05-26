package com.projetopi.tlgne.services;

import com.projetopi.tlgne.entities.*;
import com.projetopi.tlgne.repositories.DetalhesVendaRepository;
import com.projetopi.tlgne.repositories.FreteRepository;
import com.projetopi.tlgne.repositories.ProdutoRepository;
import com.projetopi.tlgne.repositories.VendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    private FreteRepository freteRepository;

    public VendaService(VendaRepository vendaRepository, DetalhesVendaRepository detalhesVendaRepository, ProdutoRepository produtoRepository, DetalhesVendaService detalhesVendaService, FreteRepository freteRepository) {
        this.vendaRepository = vendaRepository;
        this.detalhesVendaRepository = detalhesVendaRepository;
        this.produtoRepository = produtoRepository;
        this.detalhesVendaService = detalhesVendaService;
        this.freteRepository = freteRepository;
    }

    public Venda saveVenda(Venda venda) {
        gerarNumeroPedido(venda);
        venda.setStatus("Aguardando pagamento");

        if (venda.getPagamento().equals("Boleto")) {
            venda.setParcelasCartao(0);
        }

        Frete frete = freteRepository.save(venda.getFrete());
        venda.setFrete(frete);
        Venda vendaSalva = vendaRepository.save(venda);

        for (DetalhesVenda detalhesVenda : venda.getDetalhesVenda()) {
            detalhesVenda.setVenda(vendaSalva);
            detalhesVendaService.saveDetalhesVenda(detalhesVenda);
        }
        return vendaSalva;
    }

    public Venda updateVenda(Venda venda) {
        Venda vendaAlterada = new Venda();
        if (vendaRepository.existsById(venda.getId())) {
            vendaAlterada = vendaRepository.save(venda);
        }
        return vendaAlterada;
    }

    private void gerarNumeroPedido(Venda venda) {
        LocalDateTime data = LocalDateTime.now();
        String ano = String.valueOf(data.getYear());
        String mes = String.valueOf(data.getMonthValue());
        String dia = String.valueOf(data.getDayOfMonth());
        String hora = String.valueOf(data.getHour());
        String minutos = String.valueOf(data.getMinute());
        venda.setNumeroPedido(ano + mes + dia + hora + minutos
                + "-" + venda.getCliente().getId());
    }

    public List<Venda> findAll() {
        return vendaRepository.findAll();
    }

    // here
    public int findAllVenda(Date dataInico, Date dataFim) {
        List<Venda> listaVendas = vendaRepository.findVendasPorPeriodo(dataInico, dataFim);
        return listaVendas.size();
    }

    //here
    public int totalProdutosVendidos(Date dataInicio, Date dataFim) {
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

    public List<Venda> findVendaClienteById(Long id) {
        return vendaRepository.findVendaClienteById(id);
    }

    // esssa aquuii
    public List<Venda> findVendas() {
        return vendaRepository.findVendas();
    }

    public List<Venda> findVendaNumeroPedido(long id, String numeroPedido) {
        return vendaRepository.findVendaByNumeroPedido(id, numeroPedido);
    }

    public void deleteVendaById(long id) {
        vendaRepository.deleteById(id);
    }

    public List<CategoriaPorcentagem> findVendasCategoriasPorcentagem(Date dataInicio, Date dataFim) {
        List<Venda> vendas = vendaRepository.findVendasPorPeriodo(dataInicio, dataFim);
        List<CategoriaPorcentagem> vendidosCategoria = new ArrayList<>();
        List<Produto> listaProdutos = new ArrayList<>();
        if (vendas.isEmpty()) {
            vendidosCategoria.add(new CategoriaPorcentagem("Cama", 25));
            vendidosCategoria.add(new CategoriaPorcentagem("Mesa", 25));
            vendidosCategoria.add(new CategoriaPorcentagem("Banho", 25));
            vendidosCategoria.add(new CategoriaPorcentagem("Decoração", 25));
            return vendidosCategoria;
        }
        int totalProdutos = 0, cama = 0, mesa = 0, banho = 0, decoracao = 0;
        for (Venda venda : vendas) {
            for (DetalhesVenda detalheVenda : venda.getDetalhesVenda()) {
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

        vendidosCategoria.add(new CategoriaPorcentagem("Cama", camaPorcento));
        vendidosCategoria.add(new CategoriaPorcentagem("Mesa", mesaPorcento));
        vendidosCategoria.add(new CategoriaPorcentagem("Banho", banhoPorcento));
        vendidosCategoria.add(new CategoriaPorcentagem("Decoração", decoracaoPorcento));
        return vendidosCategoria;

    }

    public List<String> findVendasByDia(Date dataInicio, Date dataFim) {
        /*  List<Venda> vendas = vendaRepository.findVendasPorPeriodo(dataInicio, dataFim);
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
        }*/

        return null;
    }

    public List<MesVendas> findVendasByMes(Date dataInicio, Date dataFim) {
        List<Venda> vendas = vendaRepository.findVendasPorPeriodo(dataInicio, dataFim);
        List<MesVendas> qtdVendasMes = new ArrayList<>();

        for (int i = dataInicio.getMonth(); i <= dataFim.getMonth(); i++) {
            int cont = (int) i;
            List<Venda> vendasMes = vendas.stream().filter(x -> x.getDataVenda().getMonth() == cont)
                    .collect(Collectors.toList());
            qtdVendasMes.add(new MesVendas(Meses.values()[i].getNomeAbreviado(), vendasMes.size()));
            System.out.println(vendasMes.size());
        }
        return qtdVendasMes;
    }

}
