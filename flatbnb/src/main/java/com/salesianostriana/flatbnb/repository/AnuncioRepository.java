package com.salesianostriana.flatbnb.repository;

import com.salesianostriana.flatbnb.model.Anuncio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AnuncioRepository
        extends JpaRepository<Anuncio, UUID> {
}
