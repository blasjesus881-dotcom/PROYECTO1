package com.example.practica1.service;

import com.example.practica1.entity.Usuario;
import com.example.practica1.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public boolean login(String login, String password) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByLogin(login);

        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            // Comparación simple; en producción usa hash (BCrypt, Argon2, etc.)
            return usuario.getPassword().equals(password);
        }

        return false; // usuario no encontrado
    }
}