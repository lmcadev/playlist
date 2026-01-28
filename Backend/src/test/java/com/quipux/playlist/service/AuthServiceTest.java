package com.quipux.playlist.service;

import com.quipux.playlist.DTO.LoginDTORequest;
import com.quipux.playlist.DTO.LoginDTOResponse;
import com.quipux.playlist.model.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthService authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void iniciarSesion_CredencialesValidas_RetornaRespuesta() {
        // Preparar
        LoginDTORequest solicitud = new LoginDTORequest();
        solicitud.setCorreo("test@example.com");
        solicitud.setPassword("password123");

        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setCorreo("test@example.com");
        usuario.setNombre("Test User");

        Authentication autenticacion = mock(Authentication.class);
        when(autenticacion.getName()).thenReturn("test@example.com");
        when(autenticacion.getPrincipal()).thenReturn(usuario);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(autenticacion);
        when(jwtService.generateToken("test@example.com")).thenReturn("jwtToken");

        // Actuar
        LoginDTOResponse respuesta = authService.login(solicitud);

        // Afirmar
        assertEquals("jwtToken", respuesta.getToken());
        assertEquals(1L, respuesta.getUsuario().getId());
        assertEquals("test@example.com", respuesta.getUsuario().getCorreo());
        assertEquals("Test User", respuesta.getUsuario().getNombre());
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtService).generateToken("test@example.com");
    }

    @Test
    void iniciarSesion_CredencialesInvalidas_LanzaExcepcion() {
        // Preparar
        LoginDTORequest solicitud = new LoginDTORequest();
        solicitud.setCorreo("test@example.com");
        solicitud.setPassword("wrongPassword");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new RuntimeException("Bad credentials"));

        // Actuar y Afirmar
        assertThrows(RuntimeException.class, () -> authService.login(solicitud));
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }
}