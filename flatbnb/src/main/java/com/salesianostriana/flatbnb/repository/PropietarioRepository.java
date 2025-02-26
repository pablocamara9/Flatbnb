package com.salesianostriana.flatbnb.repository;

import com.salesianostriana.flatbnb.model.Propietario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface PropietarioRepository
        extends JpaRepository<Propietario, UUID> {

    @Query("""
        SELECT p
        FROM Propietario p
        ORDER BY p.valoracion
        """)
    List<Propietario> findAllOrderByValoracion();

    @Query("""
        SELECT p
        FROM Propietario p
        ORDER BY p.valoracion DESC
        """)
    List<Propietario> findAllOrderByValoracionDesc();

    @Query("""
        SELECT p
        FROM Propietario p
        WHERE p.valoracion BETWEEN :min AND :max
        """)
    List<Propietario> findAllByValoracionBetween(double min, double max);

    @Query("""
        SELECT p
        FROM Propietario p
        WHERE p.valoracion > :valoracion
        """)
    List<Propietario> findAllByValoracionGreaterThan(double valoracion);

    @Query("""
        SELECT p
        FROM Propietario p
        WHERE p.valoracion < :valoracion
        """)
    List<Propietario> findAllByValoracionLessThan(double valoracion);
}
