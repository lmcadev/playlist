package com.quipux.playlist.controller.usuario;

import com.quipux.playlist.DTO.usuario.UsuarioDTORequest;
import com.quipux.playlist.DTO.usuario.UsuarioDTOResponse;
import com.quipux.playlist.service.usuario.UsuarioService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    //registrar usuario
    @PostMapping
    public ResponseEntity<UsuarioDTOResponse> registrarUsuario(@Valid @RequestBody UsuarioDTORequest userDTORequest) {
        UsuarioDTOResponse response = usuarioService.guardar(userDTORequest);
        return ResponseEntity.ok(response);
    }

    //obtener todos los usuarios
    @GetMapping
    public ResponseEntity<Iterable<UsuarioDTOResponse>> obtenerUsuarios() {
        Iterable<UsuarioDTOResponse> usuarios = usuarioService.obtenerTodos();
        return ResponseEntity.ok(usuarios);
    }

    //obtener usuario por id
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTOResponse> obtenerUsuarioPorId(@PathVariable Long id) {
        UsuarioDTOResponse usuario = usuarioService.obtenerPorId(id);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(usuario);
    }

    //eliminar usuario por id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuarioPorId(@PathVariable Long id) {
        usuarioService.eliminarPorId(id);
        return ResponseEntity.noContent().build();
    }

    //editar usuario por id
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTOResponse> editarUsuarioPorId(@PathVariable Long id, @Valid @RequestBody UsuarioDTORequest usuarioDTORequest) {
        UsuarioDTOResponse usuarioActualizado = usuarioService.actualizar(id, usuarioDTORequest);
        if (usuarioActualizado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(usuarioActualizado);
    }
}