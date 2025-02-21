package com.salesianostriana.flatbnb.service;

import com.salesianostriana.flatbnb.dto.CreateUserDto;
import com.salesianostriana.flatbnb.model.User;
import com.salesianostriana.flatbnb.model.UserRole;
import com.salesianostriana.flatbnb.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User createUser(CreateUserDto createUserDto) {
        return userRepository.save(User.builder()
                .username(createUserDto.username())
                .password(passwordEncoder.encode(createUserDto.password()))
                .nombre(createUserDto.nombre())
                .apellidos(createUserDto.apellidos())
                .email(createUserDto.email())
                .telefono(createUserDto.telefono())
                .roles(Set.of(UserRole.USER))
                //.activationToken(generateRandomActicationCode())
                .build());
    }

    /*private String generateRandomActicationCode() {
        return UUID.randomUUID().toString();
    }*/


}
