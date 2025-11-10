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

            if (usuarioOpt.isEmpty()) {
                System.out.println("Usuario no encontrado en BD: " + login);
                return false;
            }

            Usuario usuario = usuarioOpt.get();
            System.out.println("Usuario encontrado en BD: " + usuario.getLogin());

            String stored = usuario.getPassword(); // puede ser null
            if (stored == null) {
                System.out.println("Password en BD es null para usuario: " + login);
                return false;
            }

            // si en BD guardaste algo como "{noop}miPass", lo eliminamos antes de comparar
            if (stored.startsWith("{noop}")) {
                stored = stored.substring("{noop}".length());
            }

            // Compara de forma segura (llama equals sobre el parámetro recibido para evitar NPE)
            boolean passwordMatch = password.equals(stored);
            System.out.println("Contraseña coincide: " + passwordMatch);

            return passwordMatch;
        } catch (Exception e) {
            System.err.println("Error en login: " + e.getMessage());
            e.printStackTrace();
            // NO lanzamos RuntimeException aquí, devolvemos false o podrías devolver status 500 en controller
            return false;
        }
    }
}