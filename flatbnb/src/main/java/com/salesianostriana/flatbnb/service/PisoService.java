package com.salesianostriana.flatbnb.service;

import com.salesianostriana.flatbnb.dto.piso.CreatePisoDto;
import com.salesianostriana.flatbnb.model.Piso;
import com.salesianostriana.flatbnb.repository.PisoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

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

    public Piso createPiso(CreatePisoDto createPisoDto) {
        return pisoRepository.save(Piso.builder()
                .direccion(createPisoDto.direccion())
                .metrosCuadrados(createPisoDto.metrosCuadrados())
                .numHabitaciones(createPisoDto.numHabitaciones())
                .observaciones(createPisoDto.observaciones())
                .build()
        );
    }

    public Piso findById(UUID id) {
        return pisoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontro el piso con id " + id));
    }


}
