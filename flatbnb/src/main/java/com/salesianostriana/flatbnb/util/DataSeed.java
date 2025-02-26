package com.salesianostriana.flatbnb.util;

import com.salesianostriana.flatbnb.model.*;
import com.salesianostriana.flatbnb.repository.AnuncioRepository;
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
    private final AnuncioRepository anuncioRepository;

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

        /*Anuncio a1 = Anuncio.builder()
                .descripcion("Piso muy luminoso")
                .precio(99.90)
                .urlImagen("Foto, mira que bonto mi piso")
                .build();
        anuncioRepository.save(a1);

        Anuncio a2 = Anuncio.builder()
                .descripcion("Piso pequeño pero acogedor")
                .precio(59.00)
                .urlImagen("Foto, mira el piso, no está mal eh")
                .build();
        anuncioRepository.save(a2);

        a1.setPropietario(p1);
        a1.addPiso(piso1);
        a2.setPropietario(p2);
        a2.addPiso(piso2);

        anuncioRepository.save(a1);
        anuncioRepository.save(a2);*/
    }
}
