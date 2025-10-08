package br.ifsp.demo.infrastructure.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sessoes")
public class SessaoEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate horarioData;
    private LocalTime horarioHora;
    private String filmeNome;
    private int filmeMinutos;

    @Column(name = "numero_sala")
    private Integer numeroSala;

    @OneToMany(mappedBy = "sessao", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<IngressoEntity> ingressos = new ArrayList<>();

    public SessaoEntity (){};

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getHorarioData() {
        return horarioData;
    }
    public void setHorarioData(LocalDate horarioData) {
        this.horarioData = horarioData;
    }


    public LocalTime getHorarioHora() {
        return horarioHora;
    }
    public void setHorarioHora(LocalTime horarioHora) {
        this.horarioHora = horarioHora;
    }

    public String getFilmeNome() {
        return filmeNome;
    }
    public void setFilmeNome(String filmeNome) {
        this.filmeNome = filmeNome;
    }

    public int getFilmeMinutos() {
        return filmeMinutos;
    }
    public void setFilmeMinutos(int filmeMinutos) {
        this.filmeMinutos = filmeMinutos;
    }

    public Integer getNumeroSala() {
        return numeroSala;
    }
    public void setNumeroSala(Integer numeroSala) {
        this.numeroSala = numeroSala;
    }

    public List<IngressoEntity> getIngressos() {
        return ingressos;
    }
    public void setIngressos(List<IngressoEntity> ingressos) {
        this.ingressos = ingressos;
    }
}
