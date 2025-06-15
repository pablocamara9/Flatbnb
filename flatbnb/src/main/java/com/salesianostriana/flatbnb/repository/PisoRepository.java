package com.salesianostriana.flatbnb.repository;

import com.salesianostriana.flatbnb.model.Anuncio;
import com.salesianostriana.flatbnb.model.Piso;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface PisoRepository
        extends JpaRepository<Piso, UUID> {

    @Query("""
        SELECT p
        FROM Piso p
        WHERE p.propietario.id = :propietarioId
    """)
    Page<Piso> findPisosByPropietarioId(UUID propietarioId, Pageable pageable);


}
