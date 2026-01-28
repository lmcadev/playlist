package com.quipux.playlist.DTO.listaReproduccion;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class ListaReproduccionDTORequest {

    @NotBlank
    private String nombre;

    @NotBlank
    private String descripcion;

    @NotEmpty
    private List<CancionDTO> canciones;
}