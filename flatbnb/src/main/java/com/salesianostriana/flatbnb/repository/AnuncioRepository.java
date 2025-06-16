package com.salesianostriana.flatbnb.repository;

import com.salesianostriana.flatbnb.model.Anuncio;
import com.salesianostriana.flatbnb.model.Propietario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface AnuncioRepository
        extends JpaRepository<Anuncio, UUID> {

    @Query("""
        SELECT a
        FROM Anuncio a
        ORDER BY a.precio
        """)
    Page<Anuncio> findAllOrderByPrecio(Pageable pageable);

    @Query("""
        SELECT a
        FROM Anuncio a
        ORDER BY a.precio DESC
        """)
    Page<Anuncio> findAllOrderByPrecioDesc(Pageable pageable);

    @Query("""
        SELECT a
        FROM Anuncio a
        WHERE a.precio BETWEEN :min AND :max
        """)
    Page<Anuncio> findAllByPrecioBetween(double min, double max, Pageable pageable);

    @Query("""
        SELECT a
        FROM Anuncio a
        WHERE a.precio > :precio
        """)
    Page<Anuncio> findAllByPrecioGreaterThan(double precio, Pageable pageable);

    @Query("""
        SELECT a
        FROM Anuncio a
        WHERE a.precio < :precio
        """)
    Page<Anuncio> findAllByPrecioLessThan(double precio, Pageable pageable);

    @Query("""
        SELECT a
        FROM Anuncio a
    """)
    Page<Anuncio> findAllPaged(Pageable pageable);

    @Query("""
        SELECT a
        FROM Anuncio a
        WHERE a.propietario.id = :propietarioID
    """)
    Page<Anuncio> findAnunciosByPropietarioId(UUID propietarioID, Pageable pageable);
}
