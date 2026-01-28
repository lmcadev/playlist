package com.quipux.playlist.service.listaRepoduccion;

import com.quipux.playlist.DTO.listaReproduccion.CancionDTO;
import com.quipux.playlist.DTO.listaReproduccion.ListaReproduccionDTORequest;
import com.quipux.playlist.DTO.listaReproduccion.ListaReproduccionDTOResponse;
import com.quipux.playlist.model.listaRepoduccion.Cancion;
import com.quipux.playlist.model.listaRepoduccion.ListaReproduccion;
import com.quipux.playlist.repository.listaReproduccion.ListaReproduccionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ListaReproduccionService {

    @Autowired
    private ListaReproduccionRepository listaReproduccionRepository;

    public ListaReproduccionDTOResponse crear(ListaReproduccionDTORequest request) {
        ListaReproduccion lista = new ListaReproduccion();
        lista.setNombre(request.getNombre());
        lista.setDescripcion(request.getDescripcion());
        lista.setCanciones(request.getCanciones().stream().map(this::convertirACancion).collect(Collectors.toList()));

        ListaReproduccion guardada = listaReproduccionRepository.save(lista);
        return convertirARespuesta(guardada);
    }

    public List<ListaReproduccionDTOResponse> obtenerTodas() {
        return listaReproduccionRepository.findAll().stream()
                .map(this::convertirARespuesta)
                .collect(Collectors.toList());
    }

    public Optional<ListaReproduccionDTOResponse> obtenerPorNombre(String nombre) {
        return listaReproduccionRepository.findById(nombre).map(this::convertirARespuesta);
    }

    public boolean borrarPorNombre(String nombre) {
        if (listaReproduccionRepository.existsById(nombre)) {
            listaReproduccionRepository.deleteById(nombre);
            return true;
        }
        return false;
    }

    private Cancion convertirACancion(CancionDTO dto) {
        Cancion cancion = new Cancion();
        cancion.setTitulo(dto.getTitulo());
        cancion.setArtista(dto.getArtista());
        cancion.setAlbum(dto.getAlbum());
        cancion.setAnno(dto.getAnno());
        cancion.setGenero(dto.getGenero());
        return cancion;
    }

    private ListaReproduccionDTOResponse convertirARespuesta(ListaReproduccion lista) {
        ListaReproduccionDTOResponse respuesta = new ListaReproduccionDTOResponse();
        respuesta.setNombre(lista.getNombre());
        respuesta.setDescripcion(lista.getDescripcion());
        respuesta.setCanciones(lista.getCanciones().stream().map(this::convertirACancionDTO).collect(Collectors.toList()));
        return respuesta;
    }

    private CancionDTO convertirACancionDTO(Cancion cancion) {
        CancionDTO dto = new CancionDTO();
        dto.setTitulo(cancion.getTitulo());
        dto.setArtista(cancion.getArtista());
        dto.setAlbum(cancion.getAlbum());
        dto.setAnno(cancion.getAnno());
        dto.setGenero(cancion.getGenero());
        return dto;
    }
}