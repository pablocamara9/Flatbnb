package com.salesianostriana.flatbnb.repository;

import com.salesianostriana.flatbnb.model.Anuncio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface AnuncioRepository
        extends JpaRepository<Anuncio, UUID> {

    @Query("""
        SELECT a
        FROM Anuncio a
        ORDER BY a.precio
        """)
    List<Anuncio> findAllOrderByPrecio();

    @Query("""
        SELECT a
        FROM Anuncio a
        ORDER BY a.precio DESC
        """)
    List<Anuncio> findAllOrderByPrecioDesc();

    @Query("""
        SELECT a
        FROM Anuncio a
        WHERE a.precio BETWEEN :min AND :max
        """)
    List<Anuncio> findAllByPrecioBetween(double min, double max);

    @Query("""
        SELECT a
        FROM Anuncio a
        WHERE a.precio > :precio
        """)
    List<Anuncio> findAllByPrecioGreaterThan(double precio);

    @Query("""
        SELECT a
        FROM Anuncio a
        WHERE a.precio < :precio
        """)
    List<Anuncio> findAllByPrecioLessThan(double precio);

}
