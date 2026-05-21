package com.greenbite.catalogo.infrastructure;

import com.greenbite.catalogo.domain.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio MongoDB para el catálogo de productos.
 * Patrón: Repository — abstrae el acceso a datos del dominio.
 */
@Repository
public interface ProductRepository extends MongoRepository<Product, String> {

    /**
     * Busca productos disponibles por ciudad.
     */
    List<Product> findByCiudadAndDisponibleTrue(String ciudad);

    /**
     * Busca productos por ciudad y categoría.
     */
    List<Product> findByCiudadAndCategoriaAndDisponibleTrue(String ciudad, String categoria);

    /**
     * Busca productos por ciudad y temporada.
     */
    List<Product> findByCiudadAndTemporadaAndDisponibleTrue(String ciudad, String temporada);
}
