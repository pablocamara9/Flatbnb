package com.salesianostriana.flatbnb.dto;

import com.salesianostriana.flatbnb.model.User;
import lombok.Builder;

import java.time.Instant;
import java.util.UUID;

@Builder
public record GetUserDto(
        UUID id,
        String username,
        String nombre,
        String apellidos,
        String email,
        String telefono,
        String role,
        boolean enabled,
        Instant createdAt
) {
    public static GetUserDto from(User user) {
        return GetUserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .nombre(user.getNombre())
                .apellidos(user.getApellidos())
                .email(user.getEmail())
                .telefono(user.getTelefono())
                .role(user.getRoles().toString())
                .enabled(user.isEnabled())
                .createdAt(user.getCreatedAt())
                .build();
    }

}
