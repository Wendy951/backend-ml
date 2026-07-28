package com.wlmc.mercaditolibre.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.wlmc.mercaditolibre.dto.AuthRequest;
import com.wlmc.mercaditolibre.dto.AuthResponse;
import com.wlmc.mercaditolibre.dto.RegistroRequest;
import com.wlmc.mercaditolibre.modelo.UsuarioEntity;
import com.wlmc.mercaditolibre.repository.UsuarioRepository;
import com.wlmc.mercaditolibre.security.JwtTokenProvider;
import com.wlmc.mercaditolibre.service.UsuarioService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UsuarioService usuarioService;
    private final UsuarioRepository usuarioRepository;

    public AuthController(AuthenticationManager authenticationManager,
        JwtTokenProvider jwtTokenProvider, UsuarioService usuarioService,
        UsuarioRepository usuarioRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.usuarioService = usuarioService;
        this.usuarioRepository = usuarioRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken 
            (request.getUsername(), request.getPassword())
        );
        
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenProvider.generateToken(authentication);

        User userPrincipal = (User) authentication.getPrincipal();
        String authority = userPrincipal.getAuthorities().stream()
        .findFirst()
        .map(auth -> auth.getAuthority())
        .orElse("ROLE_CLIENTE");

        UsuarioEntity usuario = usuarioRepository.findByUsername(userPrincipal.getUsername())
            .orElse(null);

        String nombreReal = usuario != null ? usuario.getNombre() : userPrincipal.getUsername();
        String direccion = usuario != null ? usuario.getDireccion() : null;
        String telefono = usuario != null ? usuario.getTelefono() : null;

        return ResponseEntity.ok(new AuthResponse(token, 
            userPrincipal.getUsername(), nombreReal, authority, direccion, telefono));
    }
    @PostMapping("/registro")
    public ResponseEntity<?> registro(@RequestBody RegistroRequest request) {
        try{
            UsuarioEntity usuario = usuarioService.saveUsuario(request);
            return ResponseEntity.ok(usuario);
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}