package br.ifsp.demo.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "assentos")
public class AssentoEntity {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    @Column(nullable = false)
    private String codigo;

    public AssentoEntity() {}

    public AssentoEntity(String codigo) {
        this.codigo = codigo;
    }


    @ManyToOne
    @JoinColumn(name = "sala_id")
    private SalaEntity sala;
}