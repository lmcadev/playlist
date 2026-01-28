package com.quipux.playlist.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {

    private final JwtService jwtService = new JwtService();

    @Test
    void generarToken_NombreUsuarioValido_RetornaToken() {
        // Actuar
        String token = jwtService.generateToken("test@example.com");

        // Afirmar
        assertNotNull(token);
        assertTrue(token.length() > 0);
    }

    @Test
    void extraerCorreo_TokenValido_RetornaCorreo() {
        // Preparar
        String token = jwtService.generateToken("test@example.com");

        // Actuar
        String correo = jwtService.extractCorreo(token);

        // Afirmar
        assertEquals("test@example.com", correo);
    }

    @Test
    void esTokenValido_TokenValido_RetornaVerdadero() {
        // Preparar
        String token = jwtService.generateToken("test@example.com");

        // Actuar
        boolean esValido = jwtService.isTokenValid(token);

        // Afirmar
        assertTrue(esValido);
    }

    @Test
    void esTokenValido_TokenInvalido_RetornaFalso() {
        // Actuar
        boolean esValido = jwtService.isTokenValid("invalidToken");

        // Afirmar
        assertFalse(esValido);
    }
}