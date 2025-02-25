package com.salesianostriana.flatbnb.dto.piso;

import com.salesianostriana.flatbnb.dto.propietario.GetPropietarioDto;
import com.salesianostriana.flatbnb.model.Propietario;
import lombok.Builder;

import java.util.List;

@Builder
public record GetAllPropietariosDto(
        List<GetPropietarioDto>  listadoPropietarios
) {
    public static GetAllPropietariosDto fromDto(List<Propietario> listadoPropietariosSinProcesar) {
        return GetAllPropietariosDto.builder()
                .listadoPropietarios(listadoPropietariosSinProcesar.stream().map(GetPropietarioDto::of).toList())
                .build();
    }
}
