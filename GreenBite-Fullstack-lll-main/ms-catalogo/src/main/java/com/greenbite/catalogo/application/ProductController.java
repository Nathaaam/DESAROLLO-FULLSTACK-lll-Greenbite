package com.greenbite.catalogo.application;

import com.greenbite.catalogo.domain.Product;
import com.greenbite.catalogo.infrastructure.ProductRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST del microservicio de catálogo.
 * Expone endpoints para consultar y gestionar productos.
 * Patrón: Service Layer + Repository
 */
@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductController {

    private final ProductRepository repository;

    /**
     * Lista todos los productos disponibles, con filtro opcional por ciudad.
     * GET /api/productos?ciudad=Santiago
     */
    @GetMapping
    public ResponseEntity<List<Product>> getAll(
            @RequestParam(required = false) String ciudad,
            @RequestParam(required = false) String categoria) {

        List<Product> products;

        if (ciudad != null && categoria != null) {
            products = repository.findByCiudadAndCategoriaAndDisponibleTrue(ciudad, categoria);
        } else if (ciudad != null) {
            products = repository.findByCiudadAndDisponibleTrue(ciudad);
        } else {
            products = repository.findAll();
        }

        return ResponseEntity.ok(products);
    }

    /**
     * Obtiene un producto por su ID.
     * GET /api/productos/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(@PathVariable String id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Crea un nuevo producto en el catálogo.
     * POST /api/productos
     */
    @PostMapping
    public ResponseEntity<Product> create(@Valid @RequestBody Product product) {
        product.setDisponible(product.getStock() > 0);
        return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(product));
    }

    /**
     * Actualiza un producto existente.
     * PUT /api/productos/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable String id,
                                          @Valid @RequestBody Product product) {
        return repository.findById(id).map(existing -> {
            product.setId(id);
            product.setDisponible(product.getStock() > 0);
            return ResponseEntity.ok(repository.save(product));
        }).orElse(ResponseEntity.notFound().build());
    }

    /**
     * Elimina un producto del catálogo.
     * DELETE /api/productos/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        if (!repository.existsById(id)) return ResponseEntity.notFound().build();
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
