package com.projetopi.tlgne.services;


import com.projetopi.tlgne.entities.Usuario;
import com.projetopi.tlgne.repositories.FuncionarioRepository;
import com.projetopi.tlgne.repositories.RoleRepository;
import com.projetopi.tlgne.repositories.UsuarioRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class UsuarioServiceTest {

    private UsuarioService usuarioService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private FuncionarioRepository funcionarioRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder encoder;

    @Before
    public void setUp() throws Exception {
        usuarioService = new UsuarioService(usuarioRepository, funcionarioRepository, roleRepository, encoder);
    }

    @Test
    public void develoadUserByUsername(){
        Usuario usuario = Mockito.mock(Usuario.class);
        when(usuarioRepository.findByUsername(anyString())).thenReturn(Optional.of(usuario));
        UserDetails userDetails = usuarioService.loadUserByUsername("username");
        assertNotNull(userDetails);

    }

    @Test
    public void deveSalvarUsuario(){
        Usuario usuario = Mockito.mock(Usuario.class);
        when(usuarioRepository.save(any())).thenReturn(usuario);
        Usuario usuarioRetornado = usuarioService.saveUsuario(any());
        assertEquals(usuario,usuarioRetornado);
    }

    @Test
    public void deveBuscarUsuarioPorId(){
        Usuario usuario = Mockito.mock(Usuario.class);
        when(usuarioRepository.findById(any())).thenReturn(Optional.of(usuario));
        Usuario usuarioRetornado = usuarioService.buscarUsuarioById(1);
        assertEquals(usuario,usuarioRetornado);
    }


    @Test
    public void deveVerificarSeEmailDeUsuarioJaExiste(){
        Usuario usuario = Mockito.mock(Usuario.class);
        when(usuarioRepository.findByUsername(any())).thenReturn(Optional.of(usuario));
        Usuario usuarioRetornado = usuarioService.verificarEmailExists("email");
        assertEquals(usuario,usuarioRetornado);

    }

    @Test
    public void deveDeletarUsuario(){
        usuarioService.deleteById(anyLong());
        verify(usuarioRepository, times(1)).deleteById(any());
    }

    @Test
    public void deveBuscarUsuarioPorIdd(){
        Usuario usuario = Mockito.mock(Usuario.class);
        when(usuarioRepository.findById(any())).thenReturn(Optional.of(usuario));
        Usuario usuarioRetornado = usuarioService.findById(anyLong());
        assertEquals(usuario,usuarioRetornado);
    }


}
