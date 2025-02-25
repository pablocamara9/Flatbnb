package com.salesianostriana.flatbnb.service;

import com.salesianostriana.flatbnb.model.Anuncio;
import com.salesianostriana.flatbnb.repository.AnuncioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnuncioService {

    private final AnuncioRepository anuncioRepository;

    public List<Anuncio> findAll() {
        List<Anuncio> anuncios = anuncioRepository.findAll();
        if(anuncios.isEmpty()) {
            throw new EntityNotFoundException("No se encontraron anuncios.");
        }
        return anuncios;
    }
}
