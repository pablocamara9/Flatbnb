package com.salesianostriana.flatbnb.service;

import com.salesianostriana.flatbnb.model.Piso;
import com.salesianostriana.flatbnb.repository.PisoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PisoService {

    private final PisoRepository pisoRepository;

    public List<Piso> findAll() {
        List<Piso> pisos = pisoRepository.findAll();
        if(pisos.isEmpty()) {
            throw new EntityNotFoundException("No se encontraron pisos.");
        }
        return pisos;
    }


}
