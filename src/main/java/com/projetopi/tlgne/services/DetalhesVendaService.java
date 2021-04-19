package com.projetopi.tlgne.services;


import com.projetopi.tlgne.entities.DetalhesVenda;
import com.projetopi.tlgne.repositories.DetalhesVendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DetalhesVendaService {

    @Autowired
    private DetalhesVendaRepository detalhesVendaRepository;


    public void saveDetalhesVenda(DetalhesVenda detalhesVenda) {
        detalhesVendaRepository.save(detalhesVenda);
    }

}
