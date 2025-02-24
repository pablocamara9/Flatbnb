package com.salesianostriana.flatbnb.service;

//import com.salesianostriana.flatbnb.dto.propietario.CreatePropietarioDto;
import com.salesianostriana.flatbnb.model.Propietario;
import com.salesianostriana.flatbnb.repository.PropietarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PropietarioService {

    private final PropietarioRepository propietarioRepository;

    /*public Propietario create(CreatePropietarioDto dto) {

    }*/
}
