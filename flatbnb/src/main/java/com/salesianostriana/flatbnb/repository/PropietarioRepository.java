package com.salesianostriana.flatbnb.repository;

import com.salesianostriana.flatbnb.model.Propietario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PropietarioRepository
        extends JpaRepository<Propietario, UUID> {
}
