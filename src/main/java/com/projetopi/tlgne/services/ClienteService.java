package com.projetopi.tlgne.services;

import com.projetopi.tlgne.entities.Cliente;
import com.projetopi.tlgne.entities.Funcionario;
import com.projetopi.tlgne.entities.Role;
import com.projetopi.tlgne.entities.Usuario;
import com.projetopi.tlgne.repositories.ClienteRepository;
import com.projetopi.tlgne.repositories.RoleRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    public ClienteService() {
    }

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public List<Cliente> findAll(){
        return clienteRepository.findAll();
    }

    public Cliente findAllUsuario(String usuario) {
        return clienteRepository.findAllUsuario(usuario);
    }

    
    public Cliente saveCliente(Cliente cliente){
        saveUsuarioAndUsuariosRoles(cliente);

        cliente.getUsuario().setPassword(passwordEncoder.encode(cliente.getUsuario().getPassword()));
        return clienteRepository.save(cliente);
        
    }

    private void saveUsuarioAndUsuariosRoles(Cliente cliente) {
            Set<Role> setRole = new HashSet<>();
            Role role = roleRepository.findById(2).orElseThrow(() -> new NumberFormatException());
            setRole.add(role);

            cliente.getUsuario().setRoles(setRole);
            cliente.getUsuario().setNome(cliente.getNome());
           Usuario usuario = usuarioService.saveUsuario(cliente.getUsuario());

    }

    public Cliente saveUpdateCliente(Cliente cliente) throws NotFoundException {

        if (clienteRepository.existsById(cliente.getId())) {
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
        usuarioService.saveUsuario(cliente.getUsuario());
    }

    public void deleteById(long id){
        clienteRepository.deleteById(id);
    }

    public Cliente verificarCpfJaCadastrado(String cpf) {
        return clienteRepository.findByCpf(cpf).orElse(null);
    }
}
