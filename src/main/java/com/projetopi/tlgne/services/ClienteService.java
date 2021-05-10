package com.projetopi.tlgne.services;

import com.projetopi.tlgne.entities.Cliente;
import com.projetopi.tlgne.entities.EnderecoCliente;
import com.projetopi.tlgne.entities.Role;
import com.projetopi.tlgne.entities.Usuario;
import com.projetopi.tlgne.repositories.ClienteRepository;
import com.projetopi.tlgne.repositories.RoleRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EnderecoClienteService enderecoClienteService;

    public ClienteService() {
    }

    public ClienteService(ClienteRepository clienteRepository, RoleRepository roleRepository, UsuarioService usuarioService, PasswordEncoder passwordEncoder, EnderecoClienteService enderecoClienteService) {
        this.clienteRepository = clienteRepository;
        this.roleRepository = roleRepository;
        this.usuarioService = usuarioService;
        this.passwordEncoder = passwordEncoder;
        this.enderecoClienteService = enderecoClienteService;
    }

    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    public Cliente saveCliente(Cliente cliente) {
        saveUsuarioAndUsuariosRoles(cliente);

        cliente.getUsuario().setPassword(passwordEncoder.encode(cliente.getUsuario().getPassword()));
        Cliente clienteRetornado = clienteRepository.save(cliente);
        clienteRetornado.getUsuario().setIdClienteFuncionario(clienteRetornado.getId());
        usuarioService.saveUsuario(clienteRetornado.getUsuario());
        return clienteRetornado;

    }

    private void saveUsuarioAndUsuariosRoles(Cliente cliente) {
        Set<Role> setRole = new HashSet<>();
        Role role = roleRepository.findById(2).orElseThrow(() -> new NumberFormatException());
        setRole.add(role);

        cliente.getUsuario().setRoles(setRole);
        cliente.getUsuario().setNome(cliente.getNome());
        usuarioService.saveUsuario(cliente.getUsuario());
    }

    public Cliente saveUpdateCliente(Cliente cliente) throws NotFoundException {

        if (clienteRepository.existsById(cliente.getId())) {
            if(cliente.getEnderecoCobranca() != null){
                updateEnderecoCobranca(cliente);
            }
            updateUsuarioAndUsuarios(cliente);
            return clienteRepository.save(cliente);
        }
        throw new NotFoundException("Cliente não cadastrado");
    }

    private void updateUsuarioAndUsuarios(Cliente cliente) {

        Usuario usuario = usuarioService.findById(cliente.getUsuario().getId());
        if (!cliente.getUsuario().getPassword().equals(usuario.getPassword())) {
            cliente.getUsuario().setPassword(passwordEncoder.encode(cliente.getUsuario().getPassword()));
        }

        cliente.getUsuario().setNome(cliente.getNome());
        cliente.getUsuario().setActive(cliente.getUsuario().isActive());
        usuarioService.saveUsuario(cliente.getUsuario());
    }


    private void updateEnderecoCobranca(Cliente cliente){
        cliente.getEnderecoCobranca().setCliente(cliente);
        cliente.getEnderecoCobranca().setEnderecoCobranca(true);
        EnderecoCliente enderecoCliente = enderecoClienteService.save(cliente.getEnderecoCobranca());
        cliente.setEnderecoCobranca(enderecoCliente);
    }

    public void deleteById(long id) {
        clienteRepository.deleteById(id);
    }

    public Cliente verificarCpfJaCadastrado(String cpf) {
        return clienteRepository.findByCpf(cpf).orElse(null);
    }

    public Cliente findByCliente(Long id) {
        return clienteRepository.findByCliente(id);
    }
}
