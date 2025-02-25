package com.salesianostriana.flatbnb.controller;

import com.salesianostriana.flatbnb.service.AnuncioService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/anuncio/")
public class AnuncioController {

    private final AnuncioService anuncioService;


}
