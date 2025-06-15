package com.salesianostriana.flatbnb.controller;

import com.salesianostriana.flatbnb.dto.anuncio.CreateAnuncioDto;
import com.salesianostriana.flatbnb.dto.anuncio.EditAnuncioDto;
import com.salesianostriana.flatbnb.dto.anuncio.GetAnuncioDto;
import com.salesianostriana.flatbnb.model.Anuncio;
import com.salesianostriana.flatbnb.service.AnuncioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/anuncio/")
@Tag(name = "Anuncio", description = "El controlador de los anuncios, para gestionar todas las operaciones relacionadas con ellos")
public class AnuncioController {

    private final AnuncioService anuncioService;

    @Operation(summary = "Obtiene todos los anuncios y los devuelve en forma de listado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han encontrado los anuncios",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Anuncio.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            {
                                                "id": "17c2d847-78de-4c91-8df2-7ce942442bcb",
                                                "descripcion": "AAAA EEEE IIII",
                                                "precio": 111.11,
                                                "urlImagen": "123456789",
                                                "piso": {
                                                    "id": "c1278ca6-357e-4aae-99b8-e53256c6a995",
                                                    "direccion": "Calle Falsa 124",
                                                    "metrosCuadrados": 80.0,
                                                    "numHabitaciones": 2,
                                                    "observaciones": "Piso muy acogedor",
                                                    "propietario": {
                                                        "id": "7cdb8246-f559-4171-bc87-721bb41a55e0",
                                                        "nombre": "Paco",
                                                        "apellidos": "Perez",
                                                        "email": "paco.perez@gmail.com",
                                                        "telefono": "954000001",
                                                        "valoracion": 4.0
                                                    }
                                                }
                                            }
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se encontraron los anuncios"
            )
    })
    @GetMapping
    public ResponseEntity<Page<GetAnuncioDto>> findAll(
            @PageableDefault(page = 0, size = 5) Pageable pageable
    ) {
        Page<Anuncio> pagedResult = anuncioService.findAllPaged(pageable);

        if (pagedResult.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(pagedResult.map(GetAnuncioDto::of));
    }
    /*public ResponseEntity<GetAllAnunciosDto> findAll() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(GetAllAnunciosDto.fromDto(anuncioService.findAll()));
    }*/

    @Operation(summary = "Crea un nuevo anuncio")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Anuncio creado con éxito",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Anuncio.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Datos inválidos para crear el anuncio",
                    content = @Content)
    })
    @PostMapping
    public ResponseEntity<GetAnuncioDto> create(@RequestBody @Valid CreateAnuncioDto dto) {
        Anuncio anuncio = anuncioService.create(dto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(GetAnuncioDto.of(anuncio));
    }

    @Operation(summary = "Obtiene un anuncio por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha encontrado el Anuncio",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Anuncio.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No se encontró el anuncio con el id (id proporcionado)",
                    content = @Content)
    })
    @GetMapping("{id}")
    public GetAnuncioDto findById(@PathVariable UUID id) {
        return GetAnuncioDto.of(anuncioService.findById(id));
    }

    @Operation(summary = "Edita un anuncio existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Anuncio actualizado con éxito",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Anuncio.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No se encontró el anuncio con el id (id proporcionado)",
                    content = @Content)
    })
    @PutMapping("{id}")
    public GetAnuncioDto edit(@PathVariable UUID id, @RequestBody @Valid EditAnuncioDto editAnuncioDto) {
        return GetAnuncioDto.of(anuncioService.edit(id, editAnuncioDto));
    }

    @Operation(summary = "Elimina un anuncio buscándolo por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Anuncio eliminado con éxito",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "No se encontró el anuncio con el id (id proporcionado)",
                    content = @Content)
    })
    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        anuncioService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // Consultas
    @Operation(summary = "Obtiene un un listado de anuncios ordenando por precio de menor a mayor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han encontrado los Anuncios",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Anuncio.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No se encontraron los anuncios",
                    content = @Content)
    })
    @GetMapping("/precio")
    public ResponseEntity<Page<GetAnuncioDto>> findAllOrderByPrecio(
            @PageableDefault(page = 0, size = 5) Pageable pageable
    ) {
        Page<Anuncio> pagedResult = anuncioService.findAllOrderByPrecio(pageable);

        if(pagedResult.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(pagedResult.map(GetAnuncioDto::of));
    }

    @Operation(summary = "Obtiene un un listado de anuncios ordenando por precio de mayor a menor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han encontrado los Anuncios",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Anuncio.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No se encontraron los anuncios",
                    content = @Content)
    })
    @GetMapping("/precioDesc")
    public ResponseEntity<Page<GetAnuncioDto>> findAllOrderByPrecioDesc(
            @PageableDefault(page = 0, size = 5) Pageable pageable
    ) {
        Page<Anuncio> pagedResult = anuncioService.findAllOrderByPrecioDesc(pageable);

        if(pagedResult.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(pagedResult.map(GetAnuncioDto::of));
    }

    @Operation(summary = "Obtiene un un listado de anuncios entre un rango de precios")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han encontrado los Anuncios",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Anuncio.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No se encontraron los anuncios",
                    content = @Content)
    })
    @GetMapping("/precioEntre/{min}/{max}")
    public ResponseEntity<Page<GetAnuncioDto>> findAllByPrecioBetween(@PathVariable double min, @PathVariable double max, @PageableDefault(page = 0, size = 5) Pageable pageable) {
        Page<Anuncio> pagedResult = anuncioService.findAllByPrecioBetween(min, max, pageable);

        if(pagedResult.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(pagedResult.map(GetAnuncioDto::of));
    }

    @Operation(summary = "Obtiene un un listado de anuncios con un precio mayor al proporcionado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han encontrado los Anuncios",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Anuncio.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No se encontraron los anuncios",
                    content = @Content)
    })
    @GetMapping("/precioMayor/{precio}")
    public ResponseEntity<Page<GetAnuncioDto>> findAllByPrecioGreaterThan(@PathVariable double precio, @PageableDefault(page = 0, size = 5) Pageable pageable) {
        Page<Anuncio> pagedResult = anuncioService.findAllByPrecioGreaterThan(precio, pageable);

        if(pagedResult.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(pagedResult.map(GetAnuncioDto::of));
    }

    @Operation(summary = "Obtiene un un listado de anuncios con un precio menor al proporcionado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han encontrado los Anuncios",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Anuncio.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No se encontraron los anuncios",
                    content = @Content)
    })
    @GetMapping("/precioMenor/{precio}")
    public ResponseEntity<Page<GetAnuncioDto>> findAllByPrecioLessThan(@PathVariable double precio, @PageableDefault(page = 0, size = 5) Pageable pageable) {
        Page<Anuncio> pagedResult = anuncioService.findAllByPrecioLessThan(precio, pageable);

        if(pagedResult.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(pagedResult.map(GetAnuncioDto::of));
    }

    @GetMapping("/propietario/{id}")
    public ResponseEntity<Page<GetAnuncioDto>> findAllByPropietarioId(@PathVariable UUID id, @PageableDefault(page = 0, size = 5) Pageable pageable) {
        Page<Anuncio> pagedResult = anuncioService.findAllByPropietarioId(id, pageable);

        if(pagedResult.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(pagedResult.map(GetAnuncioDto::of));
    }

}
