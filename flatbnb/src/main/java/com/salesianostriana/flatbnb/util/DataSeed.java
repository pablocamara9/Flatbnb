package com.salesianostriana.flatbnb.util;

import com.salesianostriana.flatbnb.model.Propietario;
import com.salesianostriana.flatbnb.model.User;
import com.salesianostriana.flatbnb.model.UserRole;
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

    @PostConstruct
    public void init() {

        User admin = User.builder()
                .username("admin")
                .password("admin")
                .roles(Set.of(UserRole.ADMIN))
                .build();
        userRepository.save(admin);

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
                .build();
        propietarioRepository.save(p2);

    }
}
