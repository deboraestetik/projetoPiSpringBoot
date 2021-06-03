package com.projetopi.tlgne.controllers;

import com.projetopi.tlgne.entities.CategoriaPorcentagem;
import com.projetopi.tlgne.entities.MesVendas;
import com.projetopi.tlgne.entities.Venda;
import com.projetopi.tlgne.services.VendaService;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vendas")
public class VendaController {

    @Autowired
    private VendaService vendaService;

    @GetMapping("")
    public List<Venda> findAll() {
        return vendaService.findAll();
    }

    @GetMapping("/totalVendas")
    public int findAllVenda(
            @RequestHeader("dataInicio") Date dataInicio,
            @RequestHeader("dataFim") Date  dataFim) {
        return vendaService.findAllVenda(dataInicio, dataFim);
    }

    @GetMapping("/totalProdutosVendidos")
    public int findTotalProdutosVendidos(
            @RequestHeader("dataInicio") Date dataInicio,
            @RequestHeader("dataFim") Date dataFim) {
        return vendaService.totalProdutosVendidos(dataInicio, dataFim);
    }

    @GetMapping("/find/{id}")
    public Venda findVendaById(@PathVariable(value = "id") long id) {
        return vendaService.findByVenda(id);
    }

    @GetMapping("/cliente/{id}")
    public List<Venda> findVendaClienteById(@PathVariable(value = "id") long id) {
        return vendaService.findVendaClienteById(id);
    }
    
    @GetMapping("/todasvendas")
    public List<Venda> findVendas () {
    return vendaService.findVendas();
    };
 
    @GetMapping("/categoriasPorcentagem")
    public List<CategoriaPorcentagem> findVendasCategoriasPorcentagem(
            @RequestHeader("dataInicio") Date dataInicio,
            @RequestHeader("dataFim") Date dataFim) {
        return vendaService.findVendasCategoriasPorcentagem(dataInicio, dataFim);
    }

    
    @GetMapping("/porMes")
    public List<MesVendas> findVendasByMes(
            @RequestHeader("dataInicio") Date dataInicio,
            @RequestHeader("dataFim") Date dataFim) {
        return vendaService.findVendasByMes(dataInicio, dataFim);
    }

    @GetMapping("/numeroPedido/cliente/{id}")
    public List<Venda> findVendaNumeroPedido(@PathVariable(value = "id") long id, @RequestHeader("numeroPedido") String numeroPedido) {
        return vendaService.findVendaNumeroPedido(id,numeroPedido);
    }
    @GetMapping("/numeroPedido")
    public List<Venda> findAllLike(@RequestHeader("numeroPedido") String numeroPedido) {
        return vendaService.findAllLike(numeroPedido);
    }

    @PostMapping("")
    public Venda saveVenda(@RequestBody Venda venda) {
        return vendaService.saveVenda(venda);
    }
    @PutMapping("")
    public Venda updateVenda(@RequestBody Venda venda) {
        return vendaService.updateVenda(venda);
    }

    @DeleteMapping("/{id}")
    public void deleteVendaById(@PathVariable(value = "id") long id) {
        vendaService.deleteVendaById(id);
    }


}
