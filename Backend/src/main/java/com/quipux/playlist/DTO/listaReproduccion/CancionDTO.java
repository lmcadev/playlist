package com.quipux.playlist.DTO.listaReproduccion;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CancionDTO {

    @NotBlank
    private String titulo;

    @NotBlank
    private String artista;

    @NotBlank
    private String album;

    @NotBlank
    private String anno;

    @NotBlank
    private String genero;
}