package com.salesianostriana.flatbnb.service;

//import com.salesianostriana.flatbnb.dto.propietario.CreatePropietarioDto;
import com.salesianostriana.flatbnb.model.Propietario;
import com.salesianostriana.flatbnb.repository.PropietarioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PropietarioService {

    private final PropietarioRepository propietarioRepository;

    public List<Propietario> findAll() {
        List<Propietario> propietarios = propietarioRepository.findAll();
        if(propietarios.isEmpty()) {
            throw new EntityNotFoundException("No se encontraron propietarios.");
        }
        return propietarios;
    }

    /*public Propietario create(CreatePropietarioDto dto) {

    }*/

    public Propietario findById(UUID id) {
        return propietarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontro el propietario con id " + id));
    }

}
