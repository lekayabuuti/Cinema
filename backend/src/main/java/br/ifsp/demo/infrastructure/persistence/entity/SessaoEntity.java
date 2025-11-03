package br.ifsp.demo.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "sessoes")
public class SessaoEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate data;
    private LocalTime horario;
    private String filmeNome;
    private int filmeMinutos;

    @Column(name = "numero_sala")
    private Integer numeroSala;

    @OneToMany(mappedBy = "sessao", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<IngressoEntity> ingressos = new ArrayList<>();

    public SessaoEntity (){};


}
