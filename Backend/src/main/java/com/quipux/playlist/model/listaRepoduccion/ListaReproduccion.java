package com.quipux.playlist.model.listaRepoduccion;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "listas_reproduccion")
@Data
public class ListaReproduccion {

    @Id
    @Column(unique = true, nullable = false)
    @NotBlank
    private String nombre;

    @Column(nullable = false)
    @NotBlank
    private String descripcion;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "lista_nombre")
    private List<Cancion> canciones;
}