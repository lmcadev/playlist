package com.quipux.playlist.service;

import com.quipux.playlist.DTO.listaReproduccion.CancionDTO;
import com.quipux.playlist.DTO.listaReproduccion.ListaReproduccionDTORequest;
import com.quipux.playlist.DTO.listaReproduccion.ListaReproduccionDTOResponse;
import com.quipux.playlist.model.listaRepoduccion.ListaReproduccion;
import com.quipux.playlist.repository.listaReproduccion.ListaReproduccionRepository;
import com.quipux.playlist.service.listaRepoduccion.ListaReproduccionService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ListaReproduccionServiceTest {

    @Mock
    private ListaReproduccionRepository listaReproduccionRepository;

    @InjectMocks
    private ListaReproduccionService listaReproduccionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void crear_SolicitudValida_RetornaRespuesta() {
        // Preparar
        CancionDTO cancion = new CancionDTO();
        cancion.setTitulo("Cancion 1");
        cancion.setArtista("Artista 1");
        cancion.setAlbum("Album 1");
        cancion.setAnno("2023");
        cancion.setGenero("Rock");

        ListaReproduccionDTORequest solicitud = new ListaReproduccionDTORequest();
        solicitud.setNombre("Mi Playlist");
        solicitud.setDescripcion("Una playlist genial");
        solicitud.setCanciones(Arrays.asList(cancion));

        ListaReproduccion listaGuardada = new ListaReproduccion();
        listaGuardada.setNombre("Mi Playlist");
        listaGuardada.setDescripcion("Una playlist genial");
        listaGuardada.setCanciones(new ArrayList<>());

        when(listaReproduccionRepository.save(any(ListaReproduccion.class))).thenReturn(listaGuardada);

        // Actuar
        ListaReproduccionDTOResponse respuesta = listaReproduccionService.crear(solicitud);

        // Afirmar
        assertEquals("Mi Playlist", respuesta.getNombre());
        assertEquals("Una playlist genial", respuesta.getDescripcion());
        verify(listaReproduccionRepository).save(any(ListaReproduccion.class));
    }

    @Test
    void obtenerTodas_ListasExisten_RetornaLista() {
        // Preparar
        ListaReproduccion lista = new ListaReproduccion();
        lista.setNombre("Playlist 1");
        lista.setDescripcion("Descripción 1");
        lista.setCanciones(new ArrayList<>());

        when(listaReproduccionRepository.findAll()).thenReturn(Arrays.asList(lista));

        // Actuar
        List<ListaReproduccionDTOResponse> respuesta = listaReproduccionService.obtenerTodas();

        // Afirmar
        assertEquals(1, respuesta.size());
        assertEquals("Playlist 1", respuesta.get(0).getNombre());
        verify(listaReproduccionRepository).findAll();
    }

    @Test
    void obtenerPorNombre_ListaExiste_RetornaLista() {
        // Preparar
        ListaReproduccion lista = new ListaReproduccion();
        lista.setNombre("Playlist 1");
        lista.setDescripcion("Descripción 1");
        lista.setCanciones(new ArrayList<>());

        when(listaReproduccionRepository.findById("Playlist 1")).thenReturn(Optional.of(lista));

        // Actuar
        Optional<ListaReproduccionDTOResponse> respuesta = listaReproduccionService.obtenerPorNombre("Playlist 1");

        // Afirmar
        assertTrue(respuesta.isPresent());
        assertEquals("Playlist 1", respuesta.get().getNombre());
        verify(listaReproduccionRepository).findById("Playlist 1");
    }

    @Test
    void obtenerPorNombre_ListaNoExiste_RetornaVacio() {
        // Preparar
        when(listaReproduccionRepository.findById("Playlist Inexistente")).thenReturn(Optional.empty());

        // Actuar
        Optional<ListaReproduccionDTOResponse> respuesta = listaReproduccionService.obtenerPorNombre("Playlist Inexistente");

        // Afirmar
        assertFalse(respuesta.isPresent());
        verify(listaReproduccionRepository).findById("Playlist Inexistente");
    }

    @Test
    void borrarPorNombre_ListaExiste_RetornaTrue() {
        // Preparar
        when(listaReproduccionRepository.existsById("Playlist 1")).thenReturn(true);

        // Actuar
        boolean resultado = listaReproduccionService.borrarPorNombre("Playlist 1");

        // Afirmar
        assertTrue(resultado);
        verify(listaReproduccionRepository).existsById("Playlist 1");
        verify(listaReproduccionRepository).deleteById("Playlist 1");
    }

    @Test
    void borrarPorNombre_ListaNoExiste_RetornaFalse() {
        // Preparar
        when(listaReproduccionRepository.existsById("Playlist Inexistente")).thenReturn(false);

        // Actuar
        boolean resultado = listaReproduccionService.borrarPorNombre("Playlist Inexistente");

        // Afirmar
        assertFalse(resultado);
        verify(listaReproduccionRepository).existsById("Playlist Inexistente");
        verify(listaReproduccionRepository, never()).deleteById(anyString());
    }
}