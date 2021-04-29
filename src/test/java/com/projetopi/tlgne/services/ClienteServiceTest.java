package com.projetopi.tlgne.services;


import com.projetopi.tlgne.entities.Cliente;
import com.projetopi.tlgne.repositories.ClienteRepository;
import com.projetopi.tlgne.repositories.RoleRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class ClienteServiceTest {

    private ClienteService clienteService;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private UsuarioService usuarioService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private EnderecoClienteService enderecoClienteService;

    @Before
    public void setUp() throws Exception {
        clienteService = new ClienteService(clienteRepository,roleRepository,usuarioService,passwordEncoder,enderecoClienteService);
    }

    @Test
    public void deveRetornarListadeClientes(){
        List<Cliente> clienteList = Mockito.mock(ArrayList.class);
        when(clienteRepository.findAll()).thenReturn(clienteList);
        List<Cliente> clienteListRetornado = clienteService.findAll();
        assertEquals(clienteList,clienteListRetornado);
    }

    @Test
    public void deveRetornarClienteSalvo(){

    }
}
