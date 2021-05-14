package com.projetopi.tlgne.services;


import com.projetopi.tlgne.entities.Cliente;
import com.projetopi.tlgne.entities.EnderecoCliente;
import com.projetopi.tlgne.entities.Role;
import com.projetopi.tlgne.entities.Usuario;
import com.projetopi.tlgne.repositories.ClienteRepository;
import com.projetopi.tlgne.repositories.RoleRepository;
import javassist.NotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

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
        clienteService = new ClienteService(clienteRepository, roleRepository, usuarioService, passwordEncoder, enderecoClienteService);
    }

    @Test
    public void deveRetornarListadeClientes() {
        List<Cliente> clienteList = Mockito.mock(ArrayList.class);
        when(clienteRepository.findAll()).thenReturn(clienteList);
        List<Cliente> clienteListRetornado = clienteService.findAll();
        assertEquals(clienteList, clienteListRetornado);
    }

    @Test
    public void deveRetornarClienteSalvo() {
        Usuario usuario = Mockito.mock(Usuario.class);
        Cliente cliente = Mockito.mock(Cliente.class);
        Role role = Mockito.mock(Role.class);
        when(roleRepository.findById(any())).thenReturn(Optional.of(role));
        when(cliente.getUsuario()).thenReturn(usuario);
        when(clienteRepository.save(any())).thenReturn(cliente);
        Cliente clienteRetornado = clienteService.saveCliente(cliente);
        assertEquals(cliente, clienteRetornado);
    }

    @Test
    public void deveAlterareSalvarCliente() throws NotFoundException {
        Usuario usuario = Mockito.mock(Usuario.class);
        EnderecoCliente enderecoCliente = Mockito.mock(EnderecoCliente.class);
        Cliente cliente = Mockito.mock(Cliente.class);
        when(clienteRepository.existsById(any())).thenReturn(true);
        when(cliente.getEnderecoCobranca()).thenReturn(enderecoCliente);
        when(enderecoClienteService.save(any())).thenReturn(enderecoCliente);
        when(usuarioService.findById(any())).thenReturn(usuario);
        when(cliente.getUsuario()).thenReturn(usuario);
        when(usuario.getPassword()).thenReturn("password");
        Cliente clienteRetornado = clienteService.saveUpdateCliente(cliente);
        assertNotEquals(cliente, clienteRetornado);
    }


    @Test(expected = NotFoundException.class)
    public void deveAlterareSalvarClienteException() throws NotFoundException {
        Cliente cliente = Mockito.mock(Cliente.class);
        when(clienteRepository.existsById(any())).thenReturn(false);
        clienteService.saveUpdateCliente(cliente);

    }

    @Test
    public void deveDeletarClientePorId(){
        clienteService.deleteById(anyLong());
        verify(clienteRepository, times(1)).deleteById(anyLong());
    }


    @Test
    public void deveVerificarSeCpfJaestaCadastrado(){
        Cliente cliente = Mockito.mock(Cliente.class);
        when(clienteRepository.findByCpf(anyString())).thenReturn(Optional.of(cliente));
        Cliente clienteRetornado = clienteService.verificarCpfJaCadastrado(anyString());
        assertEquals(cliente, clienteRetornado);
    }

    @Test
    public void deveBuscarCliente(){
        Cliente cliente = Mockito.mock(Cliente.class);
        when(clienteRepository.findByCliente(any())).thenReturn(cliente);
        Cliente clienteRetornado = clienteService.findByCliente(any());
        assertEquals(cliente, clienteRetornado);
    }
}
