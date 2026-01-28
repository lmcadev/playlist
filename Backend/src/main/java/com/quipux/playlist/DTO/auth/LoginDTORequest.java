package com.quipux.playlist.DTO.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginDTORequest {

    @Email
    @NotBlank
    private String correo;

    @NotBlank
    private String password;
}