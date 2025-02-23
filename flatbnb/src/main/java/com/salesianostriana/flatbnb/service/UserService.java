package com.salesianostriana.flatbnb.service;

import com.salesianostriana.flatbnb.dto.CreateUserDto;
import com.salesianostriana.flatbnb.dto.EditUserDto;
import com.salesianostriana.flatbnb.model.User;
import com.salesianostriana.flatbnb.model.UserRole;
import com.salesianostriana.flatbnb.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public List<User> findAll() {
        List<User> users = userRepository.findAll();
        if(users.isEmpty()) {
            throw new EntityNotFoundException("No se encontraron usuarios.");
        }
        return users;
    }

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

    public User findById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontro el usuario con id " + id));
    }

    public User edit(UUID id, EditUserDto dto) {
        Optional<User> aBuscar = userRepository.findById(id);
        if (aBuscar.isEmpty()) {
            throw new EntityNotFoundException("No se encontro el usuario con id " + id);
        }
        return aBuscar.map(old -> {
            old.setNombre(dto.nombre());
            old.setApellidos(dto.apellidos());
            old.setEmail(dto.email());
            old.setTelefono(dto.telefono());
            old.setPassword(passwordEncoder.encode(dto.password()));

            return userRepository.save(old);
        }).get();
    }

    public void delete(UUID id) {
        userRepository.deleteById(id);
    }


}
