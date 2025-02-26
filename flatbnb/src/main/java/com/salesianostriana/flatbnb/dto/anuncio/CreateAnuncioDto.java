package com.salesianostriana.flatbnb.dto.anuncio;

import java.util.UUID;

public record CreateAnuncioDto(
        String descripcion,
        double precio,
        String urlImagen,
        UUID idPiso,
        UUID idPropietario
) {
}
