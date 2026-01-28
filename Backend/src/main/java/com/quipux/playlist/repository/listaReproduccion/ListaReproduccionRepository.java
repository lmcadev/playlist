package com.quipux.playlist.repository.listaReproduccion;

import com.quipux.playlist.model.listaRepoduccion.ListaReproduccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListaReproduccionRepository extends JpaRepository<ListaReproduccion, String> {
}