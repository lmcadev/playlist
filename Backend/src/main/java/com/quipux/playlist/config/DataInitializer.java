package com.quipux.playlist.config;

import com.quipux.playlist.model.Usuario;
import com.quipux.playlist.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

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
    }
}