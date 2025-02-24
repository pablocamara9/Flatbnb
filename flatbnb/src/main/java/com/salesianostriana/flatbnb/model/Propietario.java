package com.salesianostriana.flatbnb.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.*;
import lombok.experimental.SuperBuilder;

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

    @OneToMany(mappedBy = "propietario", fetch = FetchType.LAZY)
    private Set<Piso> pisos;

}
