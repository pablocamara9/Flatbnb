package com.salesianostriana.flatbnb.service;

import com.salesianostriana.flatbnb.dto.anuncio.CreateAnuncioDto;
import com.salesianostriana.flatbnb.model.Anuncio;
import com.salesianostriana.flatbnb.model.Piso;
import com.salesianostriana.flatbnb.model.Propietario;
import com.salesianostriana.flatbnb.repository.AnuncioRepository;
import com.salesianostriana.flatbnb.repository.PisoRepository;
import com.salesianostriana.flatbnb.repository.PropietarioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnuncioService {

    private final AnuncioRepository anuncioRepository;
    private final PisoRepository pisoRepository;
    private final PropietarioRepository propietarioRepository;

    public List<Anuncio> findAll() {
        List<Anuncio> anuncios = anuncioRepository.findAll();
        if(anuncios.isEmpty()) {
            throw new EntityNotFoundException("No se encontraron anuncios.");
        }
        return anuncios;
    }

    public Anuncio create(CreateAnuncioDto dto) {
        Optional<Piso> piso = pisoRepository.findById(dto.idPiso());
        if (piso.isEmpty()) {
            throw new EntityNotFoundException("No se encontro el piso con id " + dto.idPiso());
        }

        Optional<Propietario> propietario = propietarioRepository.findById(dto.idPropietario());
        if (propietario.isEmpty()) {
            throw new EntityNotFoundException("No se encontro el propietario con id " + dto.idPropietario());
        }

        Anuncio a = Anuncio.builder()
                .descripcion(dto.descripcion())
                .precio(dto.precio())
                .urlImagen(dto.urlImagen())
                .piso(piso.get())
                .propietario(propietario.get())
                .build();

        a.setPiso(piso.get());
        a.setPropietario(propietario.get());
        return anuncioRepository.save(a);
    }
}
