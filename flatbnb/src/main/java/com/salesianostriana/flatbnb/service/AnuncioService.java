package com.salesianostriana.flatbnb.service;

import com.salesianostriana.flatbnb.dto.anuncio.CreateAnuncioDto;
import com.salesianostriana.flatbnb.dto.anuncio.EditAnuncioDto;
import com.salesianostriana.flatbnb.model.Anuncio;
import com.salesianostriana.flatbnb.model.Piso;
import com.salesianostriana.flatbnb.model.Propietario;
import com.salesianostriana.flatbnb.repository.AnuncioRepository;
import com.salesianostriana.flatbnb.repository.PisoRepository;
import com.salesianostriana.flatbnb.repository.PropietarioRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
                //.piso(piso.get())
                //.propietario(propietario.get())
                .build();

        a.setPiso(piso.get());
        a.setPropietario(propietario.get());
        return anuncioRepository.save(a);
    }

    public Anuncio findById(UUID id) {
        Optional<Anuncio> anuncio = anuncioRepository.findById(id);
        if(anuncio.isEmpty()) {
            throw new EntityNotFoundException("No se encontro el anuncio con id " + id);
        }
        return anuncio.get();
    }

    public Anuncio edit(UUID id, EditAnuncioDto dto) {
        Optional<Piso> piso = pisoRepository.findById(dto.idPiso());
        if (piso.isEmpty()) {
            throw new EntityNotFoundException("No se encontro el piso con id " + id);
        }
        Optional<Propietario> propietario = propietarioRepository.findById(dto.idPropietario());
        if (propietario.isEmpty()) {
            throw new EntityNotFoundException("No se encontro el propietario con id " + id);
        }

        Optional<Anuncio> aBuscar = anuncioRepository.findById(id);
        if (aBuscar.isEmpty()) {
            throw new EntityNotFoundException("No se encontro el anuncio con id " + id);
        }
        return aBuscar.map(old -> {
            old.setDescripcion(dto.descripcion());
            old.setPrecio(dto.precio());
            old.setUrlImagen(dto.urlImagen());
            old.setPiso(piso.get());
            old.setPropietario(propietario.get());

            return anuncioRepository.save(old);
        }).get();
    }

    @Transactional
    public void delete(UUID id) {
        Optional<Anuncio> aBuscar = anuncioRepository.findById(id);
        if (aBuscar.isEmpty()) {
            throw new EntityNotFoundException("No se encontro el anuncio con id " + id);
        }

        aBuscar.get().deletePiso(aBuscar.get().getPiso());
        aBuscar.get().deletePropietario(aBuscar.get().getPropietario());

        pisoRepository.saveAndFlush(aBuscar.get().getPiso());
        propietarioRepository.saveAndFlush(aBuscar.get().getPropietario());
        anuncioRepository.delete(aBuscar.get());
    }

    // Consultas
    public Page<Anuncio> findAllOrderByPrecio(Pageable pageable) {
        Page<Anuncio> anuncios = anuncioRepository.findAllOrderByPrecio(pageable);
        if(anuncios.isEmpty()) {
            throw new EntityNotFoundException("No se encontraron anuncios.");
        }
        return anuncios;
    }

    public Page<Anuncio> findAllOrderByPrecioDesc(Pageable pageable) {
        Page<Anuncio> anuncios = anuncioRepository.findAllOrderByPrecioDesc(pageable);
        if(anuncios.isEmpty()) {
            throw new EntityNotFoundException("No se encontraron anuncios.");
        }
        return anuncios;
    }

    public Page<Anuncio> findAllByPrecioBetween(double min, double max, Pageable pageable) {
        Page<Anuncio> anuncios = anuncioRepository.findAllByPrecioBetween(min, max, pageable);
        if(anuncios.isEmpty()) {
            throw new EntityNotFoundException("No se encontraron anuncios.");
        }
        return anuncios;
    }

    public Page<Anuncio> findAllByPrecioGreaterThan(double precio, Pageable pageable) {
        Page<Anuncio> anuncios = anuncioRepository.findAllByPrecioGreaterThan(precio, pageable);
        if(anuncios.isEmpty()) {
            throw new EntityNotFoundException("No se encontraron anuncios.");
        }
        return anuncios;
    }

    public Page<Anuncio> findAllByPrecioLessThan(double precio, Pageable pageable) {
        Page<Anuncio> anuncios = anuncioRepository.findAllByPrecioLessThan(precio, pageable);
        if(anuncios.isEmpty()) {
            throw new EntityNotFoundException("No se encontraron anuncios.");
        }
        return anuncios;
    }

    public Page<Anuncio> findAllPaged(Pageable pageable) {
        Page<Anuncio> anunciosPaged = anuncioRepository.findAllPaged(pageable);
        if(anunciosPaged.isEmpty()) {
            throw new EntityNotFoundException("No se encontraron anuncios.");
        }
        return anunciosPaged;
    }

    public Page<Anuncio> findAllByPropietarioId(UUID propietarioId, Pageable pageable) {
        Page<Anuncio> anuncios = anuncioRepository.findAnunciosByPropietarioId(propietarioId, pageable);
        if(anuncios.isEmpty()) {
            throw new EntityNotFoundException("No se encontraron anuncios.");
        }
        return anuncios;
    }

}
