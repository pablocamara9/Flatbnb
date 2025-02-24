package com.salesianostriana.flatbnb.util;

import com.salesianostriana.flatbnb.model.Propietario;
import com.salesianostriana.flatbnb.model.User;
import com.salesianostriana.flatbnb.model.UserRole;
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
    private final PasswordEncoder encoder;

    @PostConstruct
    public void init() {

        User p1 = Propietario.builder()
                .username("manolo")
                .password(encoder.encode("1234"))
                .nombre("Manolo")
                .apellidos("García")
                .email("manolo.garcia@gmail.com")
                .telefono("954000000")
                .enabled(true)
                .createdAt(Instant.now())
                .roles(Set.of(UserRole.USER))
                .build();

        userRepository.save(p1);

    }
}
