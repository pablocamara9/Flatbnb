package com.salesianostriana.flatbnb.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Anuncio {

    @Id
    @GeneratedValue
    private UUID id;

    private String descripcion;
    private double precio;
    private String urlImagen;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "piso_id",
            foreignKey = @ForeignKey(name = "fk_anuncio_piso"))
    private Piso piso;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "propietario_id",
            foreignKey = @ForeignKey(name = "fk_anuncio_propietario"))
    private Propietario propietario;

    public void addPiso(Piso piso) {
        this.piso = piso;
        piso.setAnuncio(this);
    }

    public void deletePiso(Piso piso) {
        piso.setAnuncio(null);
        this.piso = piso;
    }

    public void addPropietario(Propietario propietario) {
        this.propietario = propietario;
        propietario.getAnuncios().add(this);
    }

    public void deletePropietario(Propietario propietario) {
        propietario.getAnuncios().remove(this);
        this.propietario = propietario;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Anuncio anuncio = (Anuncio) o;
        return getId() != null && Objects.equals(getId(), anuncio.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
