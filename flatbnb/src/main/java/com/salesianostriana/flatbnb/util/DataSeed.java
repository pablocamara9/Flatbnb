package com.salesianostriana.flatbnb.util;

import com.salesianostriana.flatbnb.model.Piso;
import com.salesianostriana.flatbnb.model.Propietario;
import com.salesianostriana.flatbnb.model.User;
import com.salesianostriana.flatbnb.model.UserRole;
import com.salesianostriana.flatbnb.repository.PisoRepository;
import com.salesianostriana.flatbnb.repository.PropietarioRepository;
import com.salesianostriana.flatbnb.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataSeed {

    private final UserRepository userRepository;
    private final PropietarioRepository propietarioRepository;
    private final PasswordEncoder encoder;
    private final PisoRepository pisoRepository;

    @PostConstruct
    public void init() {

        User admin = User.builder()
                .username("admin")
                .password("admin")
                .roles(Set.of(UserRole.ADMIN))
                .enabled(true)
                .build();
        userRepository.save(admin);

        Piso piso1 = Piso.builder()
                .direccion("Calle Falsa 123")
                .metrosCuadrados(100)
                .numHabitaciones(3)
                .observaciones("Piso muy luminoso")
                .build();
        pisoRepository.save(piso1);

        Piso piso2 = Piso.builder()
                .direccion("Calle Falsa 124")
                .metrosCuadrados(80)
                .numHabitaciones(2)
                .observaciones("Piso muy acogedor")
                .build();
        pisoRepository.save(piso2);

        Propietario p1 = Propietario.builder()
                .username("manolo")
                .password(encoder.encode("1234"))
                .nombre("Manolo")
                .apellidos("García")
                .email("manolo.garcia@gmail.com")
                .telefono("954000000")
                .enabled(true)
                .createdAt(Instant.now())
                .roles(Set.of(UserRole.PROPIETARIO))
                .valoracion(4.5)
                //.pisos(Set.of(piso1))
                .build();
        propietarioRepository.save(p1);

        Propietario p2 = Propietario.builder()
                .username("paco")
                .password(encoder.encode("1234"))
                .nombre("Paco")
                .apellidos("Perez")
                .email("paco.perez@gmail.com")
                .telefono("954000001")
                .enabled(true)
                .createdAt(Instant.now())
                .roles(Set.of(UserRole.PROPIETARIO))
                .valoracion(4.0)
                //.pisos(Set.of(piso2))
                .build();
        propietarioRepository.save(p2);

        piso1.addPropietario(p1);
        piso2.addPropietario(p2);

        pisoRepository.save(piso1);
        pisoRepository.save(piso2);
        propietarioRepository.save(p1);
        propietarioRepository.save(p2);
    }
}
