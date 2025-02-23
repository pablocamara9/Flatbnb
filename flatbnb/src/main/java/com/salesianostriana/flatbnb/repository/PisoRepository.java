package com.salesianostriana.flatbnb.repository;

import com.salesianostriana.flatbnb.model.Piso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PisoRepository
        extends JpaRepository<Piso, Long> {
}
