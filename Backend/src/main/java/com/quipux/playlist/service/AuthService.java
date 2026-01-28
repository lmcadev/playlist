package com.quipux.playlist.service;

import com.quipux.playlist.DTO.LoginDTORequest;
import com.quipux.playlist.DTO.LoginDTOResponse;
import com.quipux.playlist.DTO.UsuarioDTOResponse;
import com.quipux.playlist.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private JwtService jwtService;

    public LoginDTOResponse login(LoginDTORequest loginDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getCorreo(), loginDTO.getPassword())
        );
        String token = jwtService.generateToken(authentication.getName());
        Usuario usuario = (Usuario) authentication.getPrincipal();
        UsuarioDTOResponse usuarioResponse = new UsuarioDTOResponse();
        usuarioResponse.setId(usuario.getId());
        usuarioResponse.setCorreo(usuario.getCorreo());
        usuarioResponse.setNombre(usuario.getNombre());
        LoginDTOResponse response = new LoginDTOResponse();
        response.setToken(token);
        response.setUsuario(usuarioResponse);
        return response;
    }
}