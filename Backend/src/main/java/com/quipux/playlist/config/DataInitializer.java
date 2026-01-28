package com.quipux.playlist.config;

import com.quipux.playlist.model.usuario.Usuario;
import com.quipux.playlist.model.listaRepoduccion.ListaReproduccion;
import com.quipux.playlist.model.listaRepoduccion.Cancion;
import com.quipux.playlist.repository.usuario.UsuarioRepository;
import com.quipux.playlist.repository.listaReproduccion.ListaReproduccionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;



@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ListaReproduccionRepository listaReproduccionRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (usuarioRepository.findByCorreo("admin@quipux.com").isEmpty()) {
            Usuario admin = new Usuario();
            admin.setCorreo("admin@quipux.com");
            admin.setNombre("administrador");
            admin.setPassword(passwordEncoder.encode("admin123"));
            usuarioRepository.save(admin);
            System.out.println("Usuario admin creado por defecto.");
        }

        if (listaReproduccionRepository.findById("Rammstein Hits").isEmpty()) {
            ListaReproduccion playlist = new ListaReproduccion();
            playlist.setNombre("Rammstein Hits");
            playlist.setDescripcion("Las mejores canciones de Rammstein");

            Cancion cancion1 = new Cancion();
            cancion1.setTitulo("Du Hast");
            cancion1.setArtista("Rammstein");
            cancion1.setAlbum("Sehnsucht");
            cancion1.setAnno("1997");
            cancion1.setGenero("Industrial Metal");

            Cancion cancion2 = new Cancion();
            cancion2.setTitulo("Sonne");
            cancion2.setArtista("Rammstein");
            cancion2.setAlbum("Mutter");
            cancion2.setAnno("2001");
            cancion2.setGenero("Industrial Metal");

            Cancion cancion3 = new Cancion();
            cancion3.setTitulo("Engel");
            cancion3.setArtista("Rammstein");
            cancion3.setAlbum("Sehnsucht");
            cancion3.setAnno("1997");
            cancion3.setGenero("Industrial Metal");

            playlist.setCanciones(Arrays.asList(cancion1, cancion2, cancion3));
            listaReproduccionRepository.save(playlist);
            System.out.println("Playlist de Rammstein creada por defecto.");
        }
    }
}