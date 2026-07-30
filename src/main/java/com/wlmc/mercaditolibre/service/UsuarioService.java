package com.wlmc.mercaditolibre.service;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.wlmc.mercaditolibre.dto.RegistroRequest;
import com.wlmc.mercaditolibre.modelo.ClienteEntity;
import com.wlmc.mercaditolibre.modelo.Rol;
import com.wlmc.mercaditolibre.modelo.UsuarioEntity;
import com.wlmc.mercaditolibre.repository.UsuarioRepository;
import com.wlmc.mercaditolibre.repository.clienteRepository;

import jakarta.transaction.Transactional;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final clienteRepository clienteRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, 
        clienteRepository clienteRepository, PasswordEncoder passwordEncoder) {
            this.usuarioRepository = usuarioRepository;
            this.clienteRepository = clienteRepository;
            this.passwordEncoder =passwordEncoder;
        }
        
        @Transactional
        public UsuarioEntity saveUsuario(RegistroRequest request) {
            if(usuarioRepository.existsByUsername(request.getUsername())) {
                throw new IllegalArgumentException("El nombre del usuario ya esta en uso ");
            }
            UsuarioEntity usuario = new UsuarioEntity();
            usuario.setUsername(request.getUsername());
            usuario.setPassword(passwordEncoder.encode(request.getPassword()));
            usuario.setNombre(request.getNombre());
            usuario.setDireccion(request.getDireccion());
            usuario.setTelefono(request.getTelefono());

            // El registro público siempre crea cuentas de Cliente.
            // (Antes se podía mandar rol:"ROLE_ADMIN" en la petición y el backend lo aceptaba sin validar nada)
            Rol rol = Rol.ROLE_CLIENTE;
            usuario.setRol(rol);
            UsuarioEntity saveUsuario = usuarioRepository.save (usuario);

            ClienteEntity cliente = new ClienteEntity();
            cliente.setNombre(request.getNombre());
            cliente.setEmail(request.getUsername());
            cliente.setDireccion(request.getDireccion());
            cliente.setTelefono(request.getTelefono());
            clienteRepository.save(cliente);

            return saveUsuario;
        }

}
