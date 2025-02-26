package com.salesianostriana.flatbnb.dto.anuncio;

import com.salesianostriana.flatbnb.model.Anuncio;
import lombok.Builder;

import java.util.List;

@Builder
public record GetAllAnunciosDto(
        List<GetAnuncioDto> listadoAnuncios
) {
    public static GetAllAnunciosDto fromDto(List<Anuncio> listadoAnunciosSinProcesar) {
        return GetAllAnunciosDto.builder()
                .listadoAnuncios(listadoAnunciosSinProcesar.stream().map(GetAnuncioDto::of).toList())
                .build();
    }
}
