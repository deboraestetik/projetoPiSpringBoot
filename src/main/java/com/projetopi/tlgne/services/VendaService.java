package com.projetopi.tlgne.services;


import com.projetopi.tlgne.entities.Venda;
import com.projetopi.tlgne.repositories.VendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VendaService {

    @Autowired
    private VendaRepository vendaRepository;

    public Venda saveVenda(Venda venda) {
        return vendaRepository.save(venda);
    }
}
