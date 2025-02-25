package com.salesianostriana.flatbnb.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder
public class Propietario extends User {

    private double valoracion;

    @Builder.Default
    @OneToMany(mappedBy = "propietario", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Piso> pisos = new HashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "propietario", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Anuncio> anuncios = new HashSet<>();

}
