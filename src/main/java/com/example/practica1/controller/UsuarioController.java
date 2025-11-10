package com.example.practica1.controller;

import com.example.practica1.service.UsuarioService;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/usuario")
@CrossOrigin(origins = "*") //  - Sin esto no funcionará desde el navegador
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/test")
    public ResponseEntity<String> test(){
        return ResponseEntity.ok("OK - Servidor funcionando correctamente");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        try {
            // Validaciones básicas
            if (request == null) {
                return ResponseEntity.badRequest().body("Request vacío");
            }

            if (request.getLogin() == null || request.getLogin().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("El login no puede estar vacío");
            }

            if (request.getPassword() == null || request.getPassword().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("La contraseña no puede estar vacía");
            }

            System.out.println("=== LOGIN ATTEMPT ===");
            System.out.println("Usuario recibido: " + request.getLogin());
            System.out.println("Contraseña recibida: [OCULTA]");

            boolean success = usuarioService.login(request.getLogin(), request.getPassword());

            if (success) {
                System.out.println("Login EXITOSO para: " + request.getLogin());
                return ResponseEntity.ok("Login exitoso");
            } else {
                System.out.println("Login FALLIDO para: " + request.getLogin());
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login fallido");
            }

        } catch (Exception e) {
            System.err.println("ERROR EN LOGIN:");
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error en el servidor: " + e.getMessage());
        }
    }

    // Clase interna para recibir JSON
    public static class LoginRequest {
        private String login;
        private String password;

        public LoginRequest() {}

        public String getLogin() {
            return login;
        }

        public void setLogin(String login) {
            this.login = login;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}