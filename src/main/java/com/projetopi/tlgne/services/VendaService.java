package com.projetopi.tlgne.services;

import com.projetopi.tlgne.entities.DetalhesVenda;
import com.projetopi.tlgne.entities.Venda;
import com.projetopi.tlgne.repositories.VendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VendaService {

    @Autowired
    private VendaRepository vendaRepository;

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

}
