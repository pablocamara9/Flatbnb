package com.salesianostriana.flatbnb.service;

import com.salesianostriana.flatbnb.dto.piso.CreatePisoDto;
import com.salesianostriana.flatbnb.dto.piso.EditPisoDto;
import com.salesianostriana.flatbnb.model.Anuncio;
import com.salesianostriana.flatbnb.model.Piso;
import com.salesianostriana.flatbnb.model.Propietario;
import com.salesianostriana.flatbnb.repository.PisoRepository;
import com.salesianostriana.flatbnb.repository.PropietarioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PisoService {

    private final PisoRepository pisoRepository;
    private final PropietarioRepository propietarioRepository;

    public List<Piso> findAll() {
        List<Piso> pisos = pisoRepository.findAll();
        if(pisos.isEmpty()) {
            throw new EntityNotFoundException("No se encontraron pisos.");
        }
        return pisos;
    }

    public Piso createPiso(CreatePisoDto createPisoDto) {
        Optional<Propietario> prop = propietarioRepository.findById(createPisoDto.idPropietario());
        if (prop.isEmpty()) {
            throw new EntityNotFoundException("No se encontro el propietario con id " + createPisoDto.idPropietario());
        }

        Piso p = Piso.builder()
                .direccion(createPisoDto.direccion())
                .metrosCuadrados(createPisoDto.metrosCuadrados())
                .numHabitaciones(createPisoDto.numHabitaciones())
                .observaciones(createPisoDto.observaciones())
                .propietario(prop.get())
                .build();

        p.setPropietario(prop.get());
        return pisoRepository.save(p);
    }

    public Piso findById(UUID id) {
        return pisoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontro el piso con id " + id));
    }

    public Piso edit(UUID id, EditPisoDto dto) {
        Optional<Piso> aBuscar = pisoRepository.findById(id);
        if(aBuscar.isEmpty()) {
            throw new EntityNotFoundException("No se encontro el piso con id " + id);
        }

        Optional<Propietario> prop = propietarioRepository.findById(dto.idPropietario());
        if (prop.isEmpty()) {
            throw new EntityNotFoundException("No se encontro el propietario con id " + dto.idPropietario());
        }

        return aBuscar.map(old -> {
            old.setDireccion(dto.direccion());
            old.setMetrosCuadrados(dto.metrosCuadrados());
            old.setNumHabitaciones(dto.numHabitaciones());
            old.setObservaciones(dto.observaciones());
            old.setPropietario(prop.get());

            return pisoRepository.save(old);
        }).get();
    }

    public void delete(UUID id) {
        pisoRepository.deleteById(id);
    }

    public Page<Piso> findAllByPropietarioId(UUID propietarioId, Pageable pageable) {
        Page<Piso> pisos = pisoRepository.findPisosByPropietarioId(propietarioId, pageable);
        if(pisos.isEmpty()) {
            throw new EntityNotFoundException("No se encontraron pisos.");
        }
        return pisos;
    }


}
