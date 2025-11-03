package br.ifsp.demo.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "salas")
public class SalaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero_sala", nullable = false, unique = true)
    private Integer numeroSala;

    @OneToMany(mappedBy = "sala", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AssentoEntity> assentos = new ArrayList<>();

    public SalaEntity() {}

    public SalaEntity(Integer numeroSala) {
        this.numeroSala = numeroSala;
    }

}