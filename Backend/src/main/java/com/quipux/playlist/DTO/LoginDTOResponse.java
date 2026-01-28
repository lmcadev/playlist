package com.quipux.playlist.DTO;

import lombok.Data;

@Data
public class LoginDTOResponse {

    private String token;
    private UsuarioDTOResponse usuario;
}