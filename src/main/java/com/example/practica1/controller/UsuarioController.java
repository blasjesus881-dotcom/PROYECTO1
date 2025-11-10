package com.example.practica1.controller;

import com.example.practica1.service.UsuarioService;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {
    @GetMapping("/test")
    public String test(){
        return "OK";
    }
    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {
        boolean success = usuarioService.login(request.getLogin(), request.getPassword());

        if (success) {
            return "Login exitoso";
        } else {
            return "Login fallido";
        }
    }

    // Clase interna para recibir JSON
    public static class LoginRequest {
        private String login;
        private String password;

        public String getLogin() { return login; }
        public void setLogin(String login) { this.login = login; }

        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }
}