package com.salesianostriana.flatbnb.service;

import com.salesianostriana.flatbnb.repository.AnuncioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnuncioService {

    private final AnuncioRepository anuncioRepository;


}
