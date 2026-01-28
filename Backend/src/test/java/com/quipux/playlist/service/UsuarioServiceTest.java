package com.quipux.playlist.service;

import com.quipux.playlist.DTO.usuario.UsuarioDTORequest;
import com.quipux.playlist.DTO.usuario.UsuarioDTOResponse;
import com.quipux.playlist.model.usuario.Usuario;
import com.quipux.playlist.repository.usuario.UsuarioRepository;
import com.quipux.playlist.service.usuario.UsuarioService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void cargarUsuarioPorNombreUsuario_UsuarioExiste_RetornaUsuario() {
        // Preparar
        Usuario usuario = new Usuario();
        usuario.setCorreo("test@example.com");
        usuario.setPassword("encodedPassword");
        when(usuarioRepository.findByCorreo("test@example.com")).thenReturn(Optional.of(usuario));

        // Actuar
        var resultado = usuarioService.loadUserByUsername("test@example.com");

        // Afirmar
        assertEquals("test@example.com", resultado.getUsername());
        assertEquals("encodedPassword", resultado.getPassword());
        verify(usuarioRepository).findByCorreo("test@example.com");
    }

    @Test
    void cargarUsuarioPorNombreUsuario_UsuarioNoEncontrado_LanzaExcepcion() {
        // Preparar
        when(usuarioRepository.findByCorreo("nonexistent@example.com")).thenReturn(Optional.empty());

        // Actuar y Afirmar
        assertThrows(UsernameNotFoundException.class, () -> usuarioService.loadUserByUsername("nonexistent@example.com"));
        verify(usuarioRepository).findByCorreo("nonexistent@example.com");
    }

    @Test
    void guardar_SolicitudValida_RetornaRespuesta() {
        // Preparar
        UsuarioDTORequest solicitud = new UsuarioDTORequest();
        solicitud.setCorreo("new@example.com");
        solicitud.setNombre("New User");
        solicitud.setPassword("password123");

        Usuario usuarioGuardado = new Usuario();
        usuarioGuardado.setId(1L);
        usuarioGuardado.setCorreo("new@example.com");
        usuarioGuardado.setNombre("New User");
        usuarioGuardado.setPassword("encodedPassword");

        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioGuardado);

        // Actuar
        UsuarioDTOResponse respuesta = usuarioService.guardar(solicitud);

        // Afirmar
        assertEquals(1L, respuesta.getId());
        assertEquals("new@example.com", respuesta.getCorreo());
        assertEquals("New User", respuesta.getNombre());
        verify(passwordEncoder).encode("password123");
        verify(usuarioRepository).save(any(Usuario.class));
    }
}