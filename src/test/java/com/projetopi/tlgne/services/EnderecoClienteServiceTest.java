package com.projetopi.tlgne.services;


import com.projetopi.tlgne.entities.Cliente;
import com.projetopi.tlgne.entities.EnderecoCliente;
import com.projetopi.tlgne.repositories.ClienteRepository;
import com.projetopi.tlgne.repositories.EnderecoClienteRepository;
import javassist.NotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class EnderecoClienteServiceTest {

    private EnderecoClienteService enderecoClienteService;

    @Mock
    private EnderecoClienteRepository enderecoClienteRepository;

    @Mock
    private ClienteRepository clienteRepository;


    @Before
    public void setUp() throws Exception {
        enderecoClienteService = new EnderecoClienteService(enderecoClienteRepository,clienteRepository);
    }

    @Test
    public void deveRetornarEnderecoClientePorId(){
        EnderecoCliente enderecoCliente = Mockito.mock(EnderecoCliente.class);
        when(enderecoClienteRepository.findById(anyLong())).thenReturn(Optional.of(enderecoCliente));
        EnderecoCliente enderecoClienteRetornado = enderecoClienteService.findById(anyLong());
        assertEquals(enderecoCliente,enderecoClienteRetornado);
    }

    @Test
    public void deveRetornarEnderecosClientePorIdCliente(){
        List<EnderecoCliente> enderecoClienteList = Mockito.mock(ArrayList.class);
        when(enderecoClienteRepository.findAllEnderecoCliente(anyLong())).thenReturn(enderecoClienteList);
        List<EnderecoCliente> enderecoClienteListRetonado = enderecoClienteService.findAllEnderecoCliente(anyLong());
        assertEquals(enderecoClienteList,enderecoClienteListRetonado);
    }

    @Test
    public void deveRetornarEnderecosClientePorIdClienteAtivos(){
        List<EnderecoCliente> enderecoClienteList = Mockito.mock(ArrayList.class);
        when(enderecoClienteRepository.findAllEnderecoClienteAtivos(anyLong())).thenReturn(enderecoClienteList);
        List<EnderecoCliente> enderecoClienteListRetonado = enderecoClienteService.findAllEnderecoClienteAtivos(anyLong());
        assertEquals(enderecoClienteList,enderecoClienteListRetonado);
    }

    @Test
    public void deveSalvarEnderecoCliente(){
        EnderecoCliente enderecoCliente = Mockito.mock(EnderecoCliente.class);
        when(enderecoClienteRepository.save(any())).thenReturn(enderecoCliente);
        EnderecoCliente enderecoClienteRetornado = enderecoClienteService.save(enderecoCliente);
        assertEquals(enderecoCliente,enderecoClienteRetornado);
    }

    @Test
    public void deveSalvarEnderecoClientePorIdCliente(){
        Cliente cliente = Mockito.mock(Cliente.class);
        List<EnderecoCliente> enderecoClienteList = Mockito.mock(ArrayList.class);
        EnderecoCliente enderecoCliente = Mockito.mock(EnderecoCliente.class);
        when(clienteRepository.findById(anyLong())).thenReturn(Optional.of(cliente));
        when(cliente.getEndereco()).thenReturn(enderecoClienteList);
        when(enderecoClienteList.isEmpty()).thenReturn(true);
        when(enderecoClienteRepository.save(any())).thenReturn(enderecoCliente);
        EnderecoCliente enderecoClienteRetornado = enderecoClienteService.saveEnderecoCliente(enderecoCliente,anyLong());
        assertEquals(enderecoCliente,enderecoClienteRetornado);
    }

    @Test
    public void deveAtualizarEnderecoClientePorIdClientePrincipal() throws NotFoundException {
        EnderecoCliente enderecoCliente = Mockito.mock(EnderecoCliente.class);
        List<EnderecoCliente> enderecoClienteList = Mockito.mock(ArrayList.class);
        when(enderecoClienteRepository.existsById(enderecoCliente.getId())).thenReturn(true);
        Cliente cliente = Mockito.mock(Cliente.class);
        when(clienteRepository.findById(anyLong())).thenReturn(Optional.of(cliente));
        when(enderecoCliente.isPrincipal()).thenReturn(true);
//        when(cliente.getEndereco()).thenReturn(enderecoClienteList);
        when(enderecoClienteRepository.save(any())).thenReturn(enderecoCliente);
        EnderecoCliente enderecoClienteRetornado = enderecoClienteService.update(enderecoCliente,2);
        assertEquals(enderecoCliente,enderecoClienteRetornado);
    }

    @Test
    public void deveAtualizarEnderecoClientePorIdClientePrincipall() throws NotFoundException {
        EnderecoCliente enderecoCliente = Mockito.mock(EnderecoCliente.class);
        List<EnderecoCliente> enderecoClienteList = new ArrayList<>();
        enderecoClienteList.add(enderecoCliente);
        when(enderecoClienteRepository.existsById(enderecoCliente.getId())).thenReturn(true);
        Cliente cliente = Mockito.mock(Cliente.class);
        when(clienteRepository.findById(anyLong())).thenReturn(Optional.of(cliente));
        when(enderecoCliente.isPrincipal()).thenReturn(true);
        when(cliente.getEndereco()).thenReturn(enderecoClienteList);
        when(enderecoClienteRepository.save(any())).thenReturn(enderecoCliente);
        EnderecoCliente enderecoClienteRetornado = enderecoClienteService.update(enderecoCliente,2);
        assertEquals(enderecoCliente,enderecoClienteRetornado);
    }

    @Test(expected = NotFoundException.class)
    public void deveLancarExceptionAtualizarEnderecoCliente() throws NotFoundException {
        EnderecoCliente enderecoCliente = Mockito.mock(EnderecoCliente.class);
        when(enderecoClienteRepository.existsById(enderecoCliente.getId())).thenReturn(false);
        enderecoClienteService.update(enderecoCliente,2);
    }


    @Test
    public void deveDeletarEnderecoCliente(){
        enderecoClienteService.delete(anyLong());
        verify(enderecoClienteRepository,times(1)).deleteById(anyLong());
    }
}
