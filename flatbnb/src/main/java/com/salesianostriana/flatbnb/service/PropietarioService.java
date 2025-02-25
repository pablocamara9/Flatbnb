package com.salesianostriana.flatbnb.service;

//import com.salesianostriana.flatbnb.dto.propietario.CreatePropietarioDto;
import com.salesianostriana.flatbnb.dto.propietario.EditPropietarioDto;
import com.salesianostriana.flatbnb.model.Propietario;
import com.salesianostriana.flatbnb.model.UserRole;
import com.salesianostriana.flatbnb.repository.PropietarioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
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

}
