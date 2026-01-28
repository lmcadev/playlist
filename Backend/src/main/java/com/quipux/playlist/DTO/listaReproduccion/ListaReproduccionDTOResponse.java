package com.quipux.playlist.DTO.listaReproduccion;

import lombok.Data;

import java.util.List;

@Data
public class ListaReproduccionDTOResponse {

    private String nombre;
    private String descripcion;
    private List<CancionDTO> canciones;
}