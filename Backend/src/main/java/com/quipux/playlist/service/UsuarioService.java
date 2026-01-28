package com.quipux.playlist.service;

import com.quipux.playlist.DTO.UsuarioDTORequest;
import com.quipux.playlist.DTO.UsuarioDTOResponse;
import com.quipux.playlist.model.Usuario;
import com.quipux.playlist.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Usuario> usuario = usuarioRepository.findByCorreo(username);
        if (usuario.isEmpty()) {
            throw new UsernameNotFoundException("Usuario no encontrado: " + username);
        }
        return usuario.get();
    }

    //guardar usuario
    public UsuarioDTOResponse guardar(UsuarioDTORequest usuarioDTO) {
        Usuario usuario = new Usuario();
        usuario.setCorreo(usuarioDTO.getCorreo());
        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setPassword(passwordEncoder.encode(usuarioDTO.getPassword()));
        Usuario saved = usuarioRepository.save(usuario);
        UsuarioDTOResponse response = new UsuarioDTOResponse();
        response.setId(saved.getId());
        response.setCorreo(saved.getCorreo());
        response.setNombre(saved.getNombre());
        return response;
    }

    //obtener todos los usuarios
    public Iterable<UsuarioDTOResponse> obtenerTodos() {
        Iterable<Usuario> usuarios = usuarioRepository.findAll();
        List<UsuarioDTOResponse> usuarioDTOResponses = new ArrayList<>();
        for (Usuario usuario : usuarios) {
            UsuarioDTOResponse dto = new UsuarioDTOResponse();
            dto.setId(usuario.getId());
            dto.setCorreo(usuario.getCorreo());
            dto.setNombre(usuario.getNombre());
            usuarioDTOResponses.add(dto);
        }
        return usuarioDTOResponses;
    }

    //obtener usuario por id
    public UsuarioDTOResponse obtenerPorId(Long id) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);
        if (usuarioOpt.isEmpty()) {
            return null;
        }
        Usuario usuario = usuarioOpt.get();
        UsuarioDTOResponse dto = new UsuarioDTOResponse();
        dto.setId(usuario.getId());
        dto.setCorreo(usuario.getCorreo());
        dto.setNombre(usuario.getNombre());
        return dto;
    }

    //eliminar usuario por id
    public void eliminarPorId(Long id) {
        usuarioRepository.deleteById(id);
    }


    //editar usuario por id
    public UsuarioDTOResponse actualizar(Long id, UsuarioDTORequest usuarioDTO) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);
        if (usuarioOpt.isEmpty()) {
            return null;
        }
        Usuario usuario = usuarioOpt.get();
        usuario.setCorreo(usuarioDTO.getCorreo());
        usuario.setNombre(usuarioDTO.getNombre());
        if (usuarioDTO.getPassword() != null && !usuarioDTO.getPassword().isEmpty()) {
            usuario.setPassword(passwordEncoder.encode(usuarioDTO.getPassword()));
        }
        Usuario updated = usuarioRepository.save(usuario);
        UsuarioDTOResponse dto = new UsuarioDTOResponse();
        dto.setId(updated.getId());
        dto.setCorreo(updated.getCorreo());
        dto.setNombre(updated.getNombre());
        return dto;
    }
}