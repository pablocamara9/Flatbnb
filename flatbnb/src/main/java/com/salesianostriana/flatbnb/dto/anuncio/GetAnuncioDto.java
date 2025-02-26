package com.salesianostriana.flatbnb.dto.anuncio;

import com.salesianostriana.flatbnb.dto.piso.GetPisoDto;
import com.salesianostriana.flatbnb.model.Anuncio;
import lombok.Builder;

import java.util.UUID;

@Builder
public record GetAnuncioDto(
        UUID id,
        String descripcion,
        double precio,
        String urlImagen,
        GetPisoDto piso
        //GetPropietarioDto propietario
) {
    public static GetAnuncioDto of(Anuncio anuncio) {
        return GetAnuncioDto.builder()
                .id(anuncio.getId())
                .descripcion(anuncio.getDescripcion())
                .precio(anuncio.getPrecio())
                .urlImagen(anuncio.getUrlImagen())
                .piso(GetPisoDto.of(anuncio.getPiso()))
                //.propietario(GetPropietarioDto.of(anuncio.getPropietario()))
                .build();
    }
}
