package com.example.practica1.service;

import com.example.practica1.dto.ClienteDTO;
import com.example.practica1.entity.Cliente;
import com.example.practica1.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repo;

    public List<Cliente> listarTodos() {
        return repo.findAll();
    }

    public Cliente guardar(Cliente c) {
        return repo.save(c);
    }

    public Cliente buscarPorId(int id) {
        return repo.findById(id).orElse(null);
    }

    public void eliminar(int id) {
        repo.deleteById(id);
    }
}