package com.salesianostriana.flatbnb.controller;

import com.salesianostriana.flatbnb.dto.GetAllPisosDto;
import com.salesianostriana.flatbnb.service.PisoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/piso/")
public class PisoController {

    private final PisoService pisoService;

    @GetMapping
    public ResponseEntity<GetAllPisosDto> findAll() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(GetAllPisosDto.fromDto(pisoService.findAll()));
    }


}
