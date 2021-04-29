package com.projetopi.tlgne.services;

import com.projetopi.tlgne.repositories.DetalhesVendaRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringJUnit4ClassRunner.class)
public class DetalhesVendaServiceTest {

    @Mock
    private DetalhesVendaRepository detalhesVendaRepository;

    private DetalhesVendaService detalhesVendaService;

    @Before
    public void setUp() throws Exception {
        detalhesVendaService = new DetalhesVendaService(detalhesVendaRepository);
    }

    @Test
    public void deveRetornarDetalhesVendaSalvo(){
        detalhesVendaService.saveDetalhesVenda(any());
        verify(detalhesVendaRepository, times(1)).save(any());
    }
}
