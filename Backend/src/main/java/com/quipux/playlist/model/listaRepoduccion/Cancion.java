package com.quipux.playlist.model.listaRepoduccion;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Table(name = "canciones")
@Data
public class Cancion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank
    private String titulo;

    @Column(nullable = false)
    @NotBlank
    private String artista;

    @Column(nullable = false)
    @NotBlank
    private String album;

    @Column(nullable = false)
    @NotBlank
    private String anno;

    @Column(nullable = false)
    @NotBlank
    private String genero;
}