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
        try {
            // Validación de parámetros
            if (login == null || login.trim().isEmpty()) {
                System.out.println("Login vacío o nulo");
                return false;
            }
            if (password == null || password.trim().isEmpty()) {
                System.out.println("Password vacío o nulo");
                return false;
            }

            System.out.println("Intentando login con usuario: " + login);

            Optional<Usuario> usuarioOpt = usuarioRepository.findByLogin(login);

            if (usuarioOpt.isPresent()) {
                Usuario usuario = usuarioOpt.get();
                System.out.println("Usuario encontrado en BD: " + usuario.getLogin());

                // Comparación simple - En producción usar BCrypt
                boolean passwordMatch = usuario.getPassword().equals(password);
                System.out.println("Contraseña coincide: " + passwordMatch);

                return passwordMatch;
            } else {
                System.out.println("Usuario no encontrado en BD: " + login);
            }

            return false;
        } catch (Exception e) {
            System.err.println("Error en login: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error al realizar login", e);
        }
    }
}