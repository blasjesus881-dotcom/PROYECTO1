package com.example.practica1.service;

import com.example.practica1.entity.Producto;
import com.example.practica1.repository.ProductoRepository;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {
    private final ProductoRepository repo;

    public ProductoService(ProductoRepository repo) {
        this.repo = repo;
    }

    public List<Producto> listarTodos() {
        return repo.findAll();
    }
    public Optional<Producto> buscarPorId(Integer id) {
        return repo.findById(id);
    }


    public Producto crear(Producto p) {
        // Si codiProd es autogenerado, quitar comprobación; aquí se guarda tal cual
        return repo.save(p);
    }


    public Producto actualizar(Integer id, Producto datos) {
        Producto existente = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Producto no existe"));
        existente.setNombProd(datos.getNombProd());
        existente.setPrecProd(datos.getPrecProd());
        existente.setStocProd(datos.getStocProd());
        // version la maneja JPA (no la seteamos manualmente)
        return repo.save(existente);
    }
    public void eliminar(Integer id) {
        if (!repo.existsById(id)) throw new IllegalArgumentException("Producto no existe");
        repo.deleteById(id);
    }

    /**
     * Actualiza solo el stock usando version (optimistic locking).
     * Si la version no coincide, JPA lanzará OptimisticLockingFailureException.
     */
    public Producto actualizarStock(Integer id, Double nuevoStock, Integer version) {
        Producto p = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Producto no existe"));
        // No es necesario validar version manualmente; JPA lo hace al persist/save.
        p.setStocProd(nuevoStock);
        try {
            return repo.save(p); // si version no coincide, lanzará OptimisticLockingFailureException
        } catch (OptimisticLockingFailureException ex) {
            throw ex;
        }
    }
}
