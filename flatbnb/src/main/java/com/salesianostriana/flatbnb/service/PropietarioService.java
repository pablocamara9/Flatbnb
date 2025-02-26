package com.salesianostriana.flatbnb.service;

//import com.salesianostriana.flatbnb.dto.propietario.CreatePropietarioDto;
import com.salesianostriana.flatbnb.dto.propietario.EditPropietarioDto;
import com.salesianostriana.flatbnb.dto.user.CreateUserDto;
import com.salesianostriana.flatbnb.model.Propietario;
import com.salesianostriana.flatbnb.model.UserRole;
import com.salesianostriana.flatbnb.repository.PropietarioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PropietarioService {

    private final PropietarioRepository propietarioRepository;
    private final PasswordEncoder encoder;

    public List<Propietario> findAll() {
        List<Propietario> propietarios = propietarioRepository.findAll();
        if(propietarios.isEmpty()) {
            throw new EntityNotFoundException("No se encontraron propietarios.");
        }
        return propietarios;
    }

    public Propietario create(CreateUserDto dto) {
        Propietario p = Propietario.builder()
                .username(dto.username())
                .password(encoder.encode(dto.password()))
                .nombre(dto.nombre())
                .apellidos(dto.apellidos())
                .email(dto.email())
                .telefono(dto.telefono())
                .valoracion(0.0)
                .roles(Set.of(UserRole.PROPIETARIO))
                .enabled(true)
                .createdAt(Instant.now())
                .build();

        return propietarioRepository.save(p);
    }

    public Propietario findById(UUID id) {
        return propietarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontro el propietario con id " + id));
    }

    public Propietario edit(UUID id, EditPropietarioDto dto) {
        Optional<Propietario> aBuscar = propietarioRepository.findById(id);
        if(aBuscar.isEmpty()) {
            throw new EntityNotFoundException("No se encontro el propietario con id " + id);
        }

        return aBuscar.map(old -> {
            old.setNombre(dto.nombre());
            old.setApellidos(dto.apellidos());
            old.setEmail(dto.email());
            old.setTelefono(dto.telefono());
            old.setPassword(dto.password());
            old.setRoles(Set.of(UserRole.PROPIETARIO));
            old.setEnabled(true);
            old.setValoracion(dto.valoracion());

            return propietarioRepository.save(old);
        }).get();
    }

    public void delete(UUID id) {
        propietarioRepository.deleteById(id);
    }

    // Consultas
    public List<Propietario> findAllOrderByValoracion() {
        List<Propietario> propietarios = propietarioRepository.findAllOrderByValoracion();
        if(propietarios.isEmpty()) {
            throw new EntityNotFoundException("No se encontraron propietarios.");
        }
        return propietarios;
    }

    public List<Propietario> findAllOrderByValoracionDesc() {
        List<Propietario> propietarios = propietarioRepository.findAllOrderByValoracionDesc();
        if(propietarios.isEmpty()) {
            throw new EntityNotFoundException("No se encontraron propietarios.");
        }
        return propietarios;
    }

    public List<Propietario> findAllByValoracionBetween(double min, double max) {
        List<Propietario> propietarios = propietarioRepository.findAllByValoracionBetween(min, max);
        if(propietarios.isEmpty()) {
            throw new EntityNotFoundException("No se encontraron propietarios.");
        }
        return propietarios;
    }

    public List<Propietario> findAllByValoracionGreaterThan(double valoracion) {
        List<Propietario> propietarios = propietarioRepository.findAllByValoracionGreaterThan(valoracion);
        if(propietarios.isEmpty()) {
            throw new EntityNotFoundException("No se encontraron propietarios.");
        }
        return propietarios;
    }

    public List<Propietario> findAllByValoracionLessThan(double valoracion) {
        List<Propietario> propietarios = propietarioRepository.findAllByValoracionLessThan(valoracion);
        if(propietarios.isEmpty()) {
            throw new EntityNotFoundException("No se encontraron propietarios.");
        }
        return propietarios;
    }

}
