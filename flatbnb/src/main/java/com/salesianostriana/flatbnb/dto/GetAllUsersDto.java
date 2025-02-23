package com.salesianostriana.flatbnb.dto;

import com.salesianostriana.flatbnb.model.User;
import lombok.Builder;

import java.util.List;

@Builder
public record GetAllUsersDto(
        List<GetUserDto> listadoUsuarios
) {
    public static GetAllUsersDto fromDto(List<User> listadoUsuariosSinProcesar) {
        return GetAllUsersDto.builder()
                .listadoUsuarios(listadoUsuariosSinProcesar.stream().map(GetUserDto::of).toList())
                .build();
    }

}
