package br.ifsp.demo.entity;

import br.ifsp.demo.domain.enumerations.Status;
import jakarta.persistence.*;

@Entity
@Table(name = "assentos")
public class AssentoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String codigo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sala_id", nullable = false)
    private SalaEntity sala;


    public AssentoEntity() {}

    public AssentoEntity(String codigo, SalaEntity sala) {
        this.codigo = codigo;
        this.sala = sala;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public SalaEntity getSala() { return sala; }
    public void setSala(SalaEntity sala) { this.sala = sala; }

}