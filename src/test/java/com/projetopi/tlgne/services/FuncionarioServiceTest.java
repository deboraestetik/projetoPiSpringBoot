package com.projetopi.tlgne.services;


import com.projetopi.tlgne.entities.EnderecoFuncionario;
import com.projetopi.tlgne.entities.Funcionario;
import com.projetopi.tlgne.entities.Role;
import com.projetopi.tlgne.entities.Usuario;
import com.projetopi.tlgne.repositories.EnderecoFuncionarioRepository;
import com.projetopi.tlgne.repositories.FuncionarioRepository;
import com.projetopi.tlgne.repositories.RoleRepository;
import javassist.NotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class FuncionarioServiceTest {

    private FuncionarioService funcionarioService;

    @Mock
    private FuncionarioRepository funcionarioRepository;

    @Mock
    private EnderecoFuncionarioRepository enderecoFuncionarioRepository;

    @Mock
    private UsuarioService usuarioService;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Before
    public void setUp() throws Exception {
        funcionarioService = new FuncionarioService(funcionarioRepository, enderecoFuncionarioRepository, usuarioService, roleRepository, passwordEncoder);
    }

    @Test
    public void deveBuscarListDeFuncionarios() {
        List<Funcionario> funcionarioList = Mockito.mock(ArrayList.class);
        when(funcionarioRepository.findAllFuncionarios()).thenReturn(funcionarioList);
        List<Funcionario> funcionarioListRetornado = funcionarioService.findAll();
        assertEquals(funcionarioList, funcionarioListRetornado);
    }

    @Test
    public void deveBuscarFuncionarioPorNome() {
        Funcionario funcionario = Mockito.mock(Funcionario.class);
        when(funcionarioRepository.findByNome(anyString())).thenReturn(funcionario);
        Funcionario funcionarioRetornado = funcionarioService.findByNomeFuncionario("funcionario");
        assertEquals(funcionario, funcionarioRetornado);

    }

    @Test
    public void deveSalvarFuncionario(){
        Funcionario funcionario = Mockito.mock(Funcionario.class);
        Role role = Mockito.mock(Role.class);
        Usuario usuario = Mockito.mock(Usuario.class);
        Long l = Long.valueOf("1").longValue();
        EnderecoFuncionario enderecoFuncionario = Mockito.mock(EnderecoFuncionario.class);
        when(funcionario.getCargo()).thenReturn("Estoquista");
        when(roleRepository.findById(any())).thenReturn(Optional.of(role));
        when(funcionario.getUsuario()).thenReturn(usuario);
        when(usuario.getId()).thenReturn(null);
        when(funcionario.getEndereco()).thenReturn(enderecoFuncionario);
        when(usuario.getPassword()).thenReturn("password");
        when(funcionarioRepository.save(any())).thenReturn(funcionario);
        when(funcionario.getId()).thenReturn(l);
        Funcionario funcionarioRetornado = funcionarioService.saveFuncionario(funcionario);
        assertEquals(funcionario, funcionarioRetornado);

    }

    @Test
    public void deveSalvarFuncionarioAdmin(){
        Funcionario funcionario = Mockito.mock(Funcionario.class);
        Role role = Mockito.mock(Role.class);
        Usuario usuario = Mockito.mock(Usuario.class);
        Long l = Long.valueOf("1").longValue();
        EnderecoFuncionario enderecoFuncionario = Mockito.mock(EnderecoFuncionario.class);
        when(funcionario.getCargo()).thenReturn("Administrador");
        when(roleRepository.findById(any())).thenReturn(Optional.of(role));
        when(funcionario.getUsuario()).thenReturn(usuario);
        when(usuario.getId()).thenReturn(null);
        when(funcionario.getEndereco()).thenReturn(enderecoFuncionario);
        when(usuario.getPassword()).thenReturn("password");
        when(funcionarioRepository.save(any())).thenReturn(funcionario);
        when(funcionario.getId()).thenReturn(l);
        Funcionario funcionarioRetornado = funcionarioService.saveFuncionario(funcionario);
        assertEquals(funcionario, funcionarioRetornado);

    }

    @Test
    public void deveAlterarESalvarFuncionario() throws NotFoundException {
        Funcionario funcionario = Mockito.mock(Funcionario.class);
        Long l = Long.valueOf("1").longValue();
        EnderecoFuncionario enderecoFuncionario = Mockito.mock(EnderecoFuncionario.class);
        Role role = Mockito.mock(Role.class);
        Usuario usuario = Mockito.mock(Usuario.class);
        Usuario usuario2 = Mockito.mock(Usuario.class);
        when(funcionario.getId()).thenReturn(l);
        when(funcionarioRepository.existsById(anyLong())).thenReturn(true);
        when(enderecoFuncionarioRepository.existsById(any())).thenReturn(true);
        when(funcionario.getEndereco()).thenReturn(enderecoFuncionario);
        when(enderecoFuncionario.getId()).thenReturn(l);
        when(funcionario.getCargo()).thenReturn("Estoquista");
        when(roleRepository.findById(any())).thenReturn(Optional.of(role));
        when(funcionario.getUsuario()).thenReturn(usuario2);
        when(usuario2.getId()).thenReturn(l);
        when(usuarioService.findById(anyLong())).thenReturn(usuario);
        when(usuario.getPassword()).thenReturn("pass");
        when(usuario2.getPassword()).thenReturn("pass");
        Funcionario funcionarioRetornado = funcionarioService.saveUpdateFuncionario(funcionario);
        assertNotEquals(funcionario, funcionarioRetornado);


    }
    @Test
    public void deveAlterarESalvarFuncionarioSenhaDiferente() throws NotFoundException {
        Funcionario funcionario = Mockito.mock(Funcionario.class);
        Long l = Long.valueOf("1").longValue();
        EnderecoFuncionario enderecoFuncionario = Mockito.mock(EnderecoFuncionario.class);
        Role role = Mockito.mock(Role.class);
        Usuario usuario = Mockito.mock(Usuario.class);
        Usuario usuario2 = Mockito.mock(Usuario.class);
        when(funcionario.getId()).thenReturn(l);
        when(funcionarioRepository.existsById(anyLong())).thenReturn(true);
        when(enderecoFuncionarioRepository.existsById(any())).thenReturn(true);
        when(funcionario.getEndereco()).thenReturn(enderecoFuncionario);
        when(enderecoFuncionario.getId()).thenReturn(l);
        when(funcionario.getCargo()).thenReturn("Estoquista");
        when(roleRepository.findById(any())).thenReturn(Optional.of(role));
        when(funcionario.getUsuario()).thenReturn(usuario2);
        when(usuario2.getId()).thenReturn(l);
        when(usuarioService.findById(anyLong())).thenReturn(usuario);
        when(usuario.getPassword()).thenReturn("pass");
        when(usuario2.getPassword()).thenReturn("password");
        Funcionario funcionarioRetornado = funcionarioService.saveUpdateFuncionario(funcionario);
        assertNotEquals(funcionario, funcionarioRetornado);


    }

//    @Test(expected = NullPointerException.class)
//    public void deveLancarExceptionsaveUpdateFuncionario() throws NotFoundException {
//        when(funcionarioRepository.existsById(anyLong())).thenReturn(false);
//        funcionarioService.saveUpdateFuncionario(any());
//    }

    @Test
    public void deveDeletarFuncionario(){
        Funcionario funcionario = Mockito.mock(Funcionario.class);
        long l = Long.valueOf("1").longValue();
        when(funcionarioRepository.findById(any())).thenReturn(Optional.of(funcionario));
        Usuario usuario = Mockito.mock(Usuario.class);
        when(funcionario.getUsuario()).thenReturn(usuario);
        when(usuario.getUsername()).thenReturn("Username");
        when(usuarioService.verificarEmailExists(anyString())).thenReturn(usuario);
        funcionarioService.deleteById(l);
        verify(usuarioService,times(1)).deleteById(usuario.getId());
        verify(funcionarioRepository,times(1)).deleteById(any());


    }

//    @Test
//    public void deveBuscarFuncionarioPorId(){
//        Funcionario funcionario = Mockito.mock(Funcionario.class);
//        when(funcionarioRepository.findById(anyLong())).thenReturn(Optional.of(funcionario));
//        Funcionario funcionarioRetornado = funcionarioService.findById(anyLong());
//        assertEquals(funcionario, funcionarioRetornado);
//
//    }

    @Test(expected = UsernameNotFoundException.class)
    public void deveBuscarFuncionarioPorIdException(){
        Funcionario funcionario = Mockito.mock(Funcionario.class);
        when(funcionarioRepository.findById(anyLong())).thenThrow(new UsernameNotFoundException("Error"));
        funcionarioService.findById(anyLong());


    }

    @Test
    public void deveVerficarSeCpfJaEstaCadastrado(){
        Funcionario funcionario = Mockito.mock(Funcionario.class);
        when(funcionarioRepository.findByCpf(anyString())).thenReturn(Optional.of(funcionario));
        Funcionario funcionarioRetornado = funcionarioService.verificarCpfJaCadastrado(anyString());
        assertEquals(funcionario, funcionarioRetornado);


    }
}
