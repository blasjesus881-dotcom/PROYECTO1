package com.example.practica1.controller;

import com.example.practica1.dto.ProductoDTO;
import com.example.practica1.dto.StockUpdateDTO;
import com.example.practica1.entity.Producto;
import com.example.practica1.service.ProductoService;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoService service;

    public ProductoController(ProductoService service) {
        this.service = service;
    }

    // LISTAR
    @GetMapping
    public ResponseEntity<List<ProductoDTO>> listar() {
        List<ProductoDTO> lista = service.listarTodos().stream().map(this::toDTO).collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    // OBTENER POR ID
    @GetMapping("/{id}")
    public ResponseEntity<ProductoDTO> obtener(@PathVariable Integer id) {
        return service.buscarPorId(id)
                .map(p -> ResponseEntity.ok(toDTO(p)))
                .orElse(ResponseEntity.notFound().build());
    }

    // CREAR
    @PostMapping
    public ResponseEntity<ProductoDTO> crear(@RequestBody ProductoDTO dto) {
        Producto p = toEntity(dto);
        Producto creado = service.crear(p);
        URI location = URI.create(String.format("/api/productos/%d", creado.getCodiProd()));
        return ResponseEntity.created(location).body(toDTO(creado));
    }


    // ACTUALIZAR COMPLETO
    @PutMapping("/{id}")
    public ResponseEntity<ProductoDTO> actualizar(@PathVariable Integer id, @RequestBody ProductoDTO dto) {
        try {
            Producto datos = toEntity(dto);
            Producto actualizado = service.actualizar(id, datos);
            return ResponseEntity.ok(toDTO(actualizado));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.notFound().build();
        }
    }
    // ELIMINAR
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        try {
            service.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/stock")
    public ResponseEntity<?> actualizarStock(@PathVariable Integer id, @RequestBody StockUpdateDTO dto) {
        try {
            Producto actualizado = service.actualizarStock(id, dto.getStocProd(), dto.getVersion());
            return ResponseEntity.ok(actualizado);
        } catch (OptimisticLockingFailureException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Conflicto: la versión del producto ya cambió. Recarga y reintenta.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
    // Helpers: mapeo Entity <-> DTO
    private ProductoDTO toDTO(Producto p) {
        ProductoDTO dto = new ProductoDTO();
        dto.setCodiProd(p.getCodiProd());
        dto.setNombProd(p.getNombProd());
        dto.setPrecProd(p.getPrecProd());
        dto.setStocProd(p.getStocProd());
        dto.setVersion(p.getVersion());
        return dto;
    }
    private Producto toEntity(ProductoDTO dto) {
        Producto p = new Producto();
        p.setCodiProd(dto.getCodiProd());
        p.setNombProd(dto.getNombProd());
        p.setPrecProd(dto.getPrecProd());
        p.setStocProd(dto.getStocProd());
        // no setVersion: JPA lo maneja
        return p;
    }





}
