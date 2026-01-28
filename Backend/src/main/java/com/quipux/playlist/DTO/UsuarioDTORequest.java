package com.quipux.playlist.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UsuarioDTORequest {

    @Email
    @NotBlank
    private String correo;

    @NotBlank
    private String nombre;

    @NotBlank
    private String password;
}