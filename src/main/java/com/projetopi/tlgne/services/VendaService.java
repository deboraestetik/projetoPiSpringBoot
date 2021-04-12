package com.projetopi.tlgne.services;


import com.projetopi.tlgne.entities.DetalhesVenda;
import com.projetopi.tlgne.entities.Venda;
import com.projetopi.tlgne.repositories.VendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VendaService {

    @Autowired
    private VendaRepository vendaRepository;

    @Autowired
    private DetalhesVendaService detalhesVendaService;

    public Venda saveVenda(Venda venda) {

        for (DetalhesVenda detalhesVenda: venda.getDetalhesVenda()){
            detalhesVendaService.saveDetalhesVenda(detalhesVenda);
        }
        return vendaRepository.save(venda);
    }
}
