package com.quipux.playlist.controller.listaRepoduccion;

import com.quipux.playlist.DTO.listaReproduccion.ListaReproduccionDTORequest;
import com.quipux.playlist.DTO.listaReproduccion.ListaReproduccionDTOResponse;
import com.quipux.playlist.service.listaRepoduccion.ListaReproduccionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/lists")
public class ListaReproduccionController {

    @Autowired
    private ListaReproduccionService listaReproduccionService;

    @PostMapping
    public ResponseEntity<ListaReproduccionDTOResponse> crearLista(@Valid @RequestBody ListaReproduccionDTORequest request) {
        ListaReproduccionDTOResponse respuesta = listaReproduccionService.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }

    @GetMapping
    public ResponseEntity<List<ListaReproduccionDTOResponse>> obtenerTodasLasListas() {
        List<ListaReproduccionDTOResponse> listas = listaReproduccionService.obtenerTodas();
        return ResponseEntity.ok(listas);
    }

    @GetMapping("/{listName}")
    public ResponseEntity<ListaReproduccionDTOResponse> obtenerListaPorNombre(@PathVariable String listName) {
        return listaReproduccionService.obtenerPorNombre(listName)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{listName}")
    public ResponseEntity<Void> borrarListaPorNombre(@PathVariable String listName) {
        boolean borrada = listaReproduccionService.borrarPorNombre(listName);
        if (borrada) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}