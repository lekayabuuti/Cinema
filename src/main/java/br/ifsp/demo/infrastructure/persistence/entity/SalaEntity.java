package br.ifsp.demo.infrastructure.persistence.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Integer getNumeroSala() { return numeroSala; }
    public void setNumeroSala(Integer numeroSala) { this.numeroSala = numeroSala; }

    public List<AssentoEntity> getAssentos() { return assentos; }
    public void setAssentos(List<AssentoEntity> assentos) { this.assentos = assentos; }
}