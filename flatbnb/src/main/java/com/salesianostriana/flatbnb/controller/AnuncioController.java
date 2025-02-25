package com.salesianostriana.flatbnb.controller;

import com.salesianostriana.flatbnb.dto.piso.GetAllAnunciosDto;
import com.salesianostriana.flatbnb.service.AnuncioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
