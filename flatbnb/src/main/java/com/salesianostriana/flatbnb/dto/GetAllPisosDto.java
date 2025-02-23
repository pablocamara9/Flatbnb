package com.salesianostriana.flatbnb.dto;

import com.salesianostriana.flatbnb.model.Piso;
import lombok.Builder;

import java.util.List;

@Builder
public record GetAllPisosDto(
        List<GetPisoDto> listadoPisos
) {
    public static GetAllPisosDto fromDto(List<Piso> listadoPisosSinProcesar) {
        return GetAllPisosDto.builder()
                .listadoPisos(listadoPisosSinProcesar.stream().map(GetPisoDto::of).toList())
                .build();
    }

}
