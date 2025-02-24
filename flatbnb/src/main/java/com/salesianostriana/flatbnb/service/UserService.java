package com.salesianostriana.flatbnb.service;

import com.salesianostriana.flatbnb.dto.user.CreateUserDto;
import com.salesianostriana.flatbnb.dto.user.EditUserDto;
import com.salesianostriana.flatbnb.error.ActivationExpiredException;
import com.salesianostriana.flatbnb.model.User;
import com.salesianostriana.flatbnb.model.UserRole;
import com.salesianostriana.flatbnb.repository.UserRepository;
import com.salesianostriana.flatbnb.util.SendGridMailSender;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final SendGridMailSender mailSender;

    @Value("${activation.duration}")
    private int activationDuration;

    public List<User> findAll() {
        List<User> users = userRepository.findAll();
        if(users.isEmpty()) {
            throw new EntityNotFoundException("No se encontraron usuarios.");
        }
        return users;
    }

    public User createUser(CreateUserDto createUserDto) {
        User user = User.builder()
                .username(createUserDto.username())
                .password(passwordEncoder.encode(createUserDto.password()))
                .nombre(createUserDto.nombre())
                .apellidos(createUserDto.apellidos())
                .email(createUserDto.email())
                .telefono(createUserDto.telefono())
                .roles(Set.of(UserRole.USER))
                .activationToken(generateRandomActivationCode())
                .build();

        try {
            mailSender.sendEmail(createUserDto.email(), "Activación de cuenta", user.getActivationToken());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al enviar el email de activación.");
        }

        return userRepository.save(user);
    }

    private String generateRandomActivationCode() {
        return UUID.randomUUID().toString();
    }

    public User activateAccount(String token) {
        return userRepository.findByActivationToken(token)
                .filter(user -> ChronoUnit.MINUTES.between(Instant.now(), user.getCreatedAt()) - activationDuration < 0)
                .map(user -> {
                    user.setEnabled(true);
                    user.setActivationToken(null);
                    return userRepository.save(user);
                })
                .orElseThrow(() -> new ActivationExpiredException("El código de activación no existe o ha caducado"));
    }

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
