package com.salesianostriana.flatbnb.controller;

import com.salesianostriana.flatbnb.dto.anuncio.CreateAnuncioDto;
import com.salesianostriana.flatbnb.dto.anuncio.GetAllAnunciosDto;
import com.salesianostriana.flatbnb.dto.anuncio.GetAnuncioDto;
import com.salesianostriana.flatbnb.model.Anuncio;
import com.salesianostriana.flatbnb.service.AnuncioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/anuncio/")
public class AnuncioController {

    private final AnuncioService anuncioService;

    @GetMapping
    public ResponseEntity<GetAllAnunciosDto> findAll() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(GetAllAnunciosDto.fromDto(anuncioService.findAll()));
    }

    @PostMapping
    public ResponseEntity<GetAnuncioDto> create(@RequestBody CreateAnuncioDto dto) {
        Anuncio anuncio = anuncioService.create(dto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(GetAnuncioDto.of(anuncio));
    }

    @GetMapping("{id}")
    public GetAnuncioDto findById(@PathVariable UUID id) {
        return GetAnuncioDto.of(anuncioService.findById(id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        anuncioService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
