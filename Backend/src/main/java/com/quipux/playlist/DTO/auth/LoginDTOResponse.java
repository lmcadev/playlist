package com.quipux.playlist.DTO.auth;

import com.quipux.playlist.DTO.usuario.UsuarioDTOResponse;

import lombok.Data;

@Data
public class LoginDTOResponse {

    private String token;
    private UsuarioDTOResponse usuario;
}